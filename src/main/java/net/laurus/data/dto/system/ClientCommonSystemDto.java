package net.laurus.data.dto.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Value;
import net.laurus.data.dto.system.esxi.EsxiCpuCoreDto;
import net.laurus.data.dto.system.esxi.EsxiSystemDataDto;
import net.laurus.data.dto.system.lmsensors.RustClientData;
import net.laurus.data.dto.system.lmsensors.RustClientData.CpuPackageDataDto;
import net.laurus.data.dto.system.lmsensors.RustClientData.CpuPackageDataDto.CpuCoreDataDto;
import net.laurus.network.IPv4Address;

@Value
@Builder
public class ClientCommonSystemDto {

	// System Information
    private String hostname;
    private IPv4Address systemIp;    
    private String osVersion;

    private long uptime;
    private ClientCommonCpuDetailDto cpuInformation;
    private ClientCommonMemoryDto memoryInformation;
    

    public static ClientCommonSystemDto from(RustClientData rustClient) {

        // Map to group cores by socketId
        Map<Integer, List<ClientCommonCpuCoreDto>> coresBySocket = new HashMap<>();

        // Build CPU package information based on grouped cores
        List<ClientCommonCpuPackageDto> cpuPackageInfo = new ArrayList<>();

        // Process core information and group them by socketId
        int socketId = 0;
        int coreId = 0;

        for (CpuPackageDataDto cpuPackage : rustClient.getCpuData()) {
            for (CpuCoreDataDto core : cpuPackage.getCoreData()) {
                ClientCommonCpuCoreDto coreDto = ClientCommonCpuCoreDto.builder()
                        .coreId(coreId)
                        .coreName(core.getCoreName())
                        .digitalReadout(-1) // Unsupported for now
                        .physicalCoreId(coreId)
                        .socketId(socketId)
                        .temperature((int) core.getTemperature())
                        .virtualThread(coreId % 2 == 0)
                        .build();

                coresBySocket.computeIfAbsent(socketId++, k -> new ArrayList<>()).add(coreDto);
                coreId++;
            }
        }

        // Now process the `coresBySocket` map to build `cpuPackageInfo`
        for (Map.Entry<Integer, List<ClientCommonCpuCoreDto>> entry : coresBySocket.entrySet()) {
            int packageId = entry.getKey();
            List<ClientCommonCpuCoreDto> coreData = entry.getValue();

            // Calculate package temperature (e.g., average core temperature)
            float packageTemperature = coreData.stream()
                    .map(ClientCommonCpuCoreDto::getTemperature)
                    .reduce(0, Integer::sum) / (float) coreData.size();

            cpuPackageInfo.add(ClientCommonCpuPackageDto.builder()
                    .coreData(coreData)
                    .packageId("socket-" + packageId)
                    .packageTemperature(packageTemperature)
                    .build());
        }


        // Build CPU details
        ClientCommonCpuDetailDto cpuDetails = ClientCommonCpuDetailDto.builder()
                .coresPerSocket(rustClient.getCpuInfo().getCoreCount() / rustClient.getCpuData().size())
                .cpuArch("x86_64") // Assuming a default architecture; adjust as needed
                .logicalProcessors(rustClient.getCpuInfo().getCoreCount())
                .packageInfo(cpuPackageInfo)
                .sockets(rustClient.getCpuData().size())
                .threadsPerCore(2) // TODO - Verify logic somewhere
                .build();

        ClientCommonMemoryDto memoryInfo = new ClientCommonMemoryDto(
        		rustClient.getMemoryInfo().getTotal(),
        		rustClient.getMemoryInfo().getUsed()
        );

        // Build the final ClientSystemInfoDto
        return ClientCommonSystemDto.builder()
                .cpuInformation(cpuDetails)
                .hostname(rustClient.getSystemInfo().getHostname())
                .memoryInformation(memoryInfo)
                .osVersion("Unknown") // TODO - Implement someday
                .systemIp(new IPv4Address(rustClient.getSystemInfo().getManagementIp()))
                .uptime(rustClient.getSystemInfo().getUptime().getTotalSeconds())
                .build();
    }
    
    public static ClientCommonSystemDto from(EsxiSystemDataDto esxiClient) {

        // Map to group cores by socketId
        Map<Integer, List<ClientCommonCpuCoreDto>> coresBySocket = new HashMap<>();

        // Process core information and group them by socketId
        for (EsxiCpuCoreDto core : esxiClient.getCpu().getData()) {
            ClientCommonCpuCoreDto coreDto = ClientCommonCpuCoreDto.builder()
                    .coreId(core.getCoreId())
                    .coreName("core-" + core.getCoreId())
                    .digitalReadout(core.getDigitalReadout())
                    .physicalCoreId(core.getPhysicalCoreId())
                    .socketId(core.getSocketId())
                    .temperature(core.getTemperature())
                    .virtualThread(core.isVirtualThread())
                    .build();

            coresBySocket.computeIfAbsent(core.getSocketId(), k -> new ArrayList<>()).add(coreDto);
        }

        // Build CPU package information based on grouped cores
        List<ClientCommonCpuPackageDto> cpuPackageInfo = new ArrayList<>();
        for (Map.Entry<Integer, List<ClientCommonCpuCoreDto>> entry : coresBySocket.entrySet()) {
            int socketId = entry.getKey();
            List<ClientCommonCpuCoreDto> coreData = entry.getValue();

            // Calculate package temperature (example: average core temperature)
            float packageTemperature = coreData.stream()
                    .map(ClientCommonCpuCoreDto::getTemperature)
                    .reduce(0, Integer::sum) / (float) coreData.size();

            cpuPackageInfo.add(ClientCommonCpuPackageDto.builder()
                    .coreData(coreData)
                    .packageId("socket-" + socketId)
                    .packageTemperature(packageTemperature)
                    .build());
        }

        // Build CPU details
        ClientCommonCpuDetailDto cpuDetails = ClientCommonCpuDetailDto.builder()
                .coresPerSocket(esxiClient.getCpu().getInfo().getCoresPerSocket())
                .cpuArch("x86_64") // Assuming a default architecture; adjust as needed
                .logicalProcessors(esxiClient.getCpu().getInfo().getLogicalProcessors())
                .packageInfo(cpuPackageInfo)
                .sockets(esxiClient.getCpu().getInfo().getSockets())
                .threadsPerCore(esxiClient.getCpu().getInfo().getThreadsPerCore())
                .build();

        ClientCommonMemoryDto memoryInfo = new ClientCommonMemoryDto(
                esxiClient.getSystem().getTotalMemoryBytes(),
                -1 // TODO (Implement someday)
        );

        // Build the final ClientSystemInfoDto
        return ClientCommonSystemDto.builder()
                .cpuInformation(cpuDetails)
                .hostname(esxiClient.getSystem().getHostname())
                .memoryInformation(memoryInfo)
                .osVersion(esxiClient.getSystem().getKernelVersion())
                .systemIp(new IPv4Address(esxiClient.getSystem().getManagementIp()))
                .uptime(esxiClient.getSystem().getUptimeSeconds())
                .build();
    }

}
