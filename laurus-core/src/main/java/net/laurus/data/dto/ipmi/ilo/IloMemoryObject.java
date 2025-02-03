package net.laurus.data.dto.ipmi.ilo;

import static net.laurus.Constant.JSON_MAPPER;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;
import net.laurus.data.enums.ilo.HpMemoryErrorCorrection;
import net.laurus.data.enums.ilo.HpMemoryStatus;
import net.laurus.data.enums.ilo.HpMemoryType;
import net.laurus.data.enums.system.DimmGeneration;
import net.laurus.data.enums.system.DimmTechnology;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithAuth;
import net.laurus.network.IPv4Address;
import net.laurus.util.JsonUtil;
import net.laurus.util.NetworkUtil;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IloMemoryObject implements IloUpdatableFeatureWithAuth {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
	@Builder
	public static class IloMemoryDimm implements IloUpdatableFeatureWithAuth {

		private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

		@NonFinal
		@Setter
		HpMemoryStatus status;
		DimmLocation location;
		DimmTechnology type;
		DimmGeneration generation;
		HpMemoryErrorCorrection ecc;
		HpMemoryType hpMemoryType;
		String id;
		String name;
		String manufacturer;
		String partNumber;
		int rank;
		int size;
		int maximumFrequency;
		int minimumVoltageVolts;
		int dataWidth;
		int totalWidth;
		@NonFinal
		long lastUpdateTime;

		@Value
		public static class DimmLocation implements NetworkData {

			private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

			int processorId;
			int dimmId;

			private static DimmLocation from(String internalName) {
				String[] socketLocator = internalName.stripTrailing().replace("  ", " ").split(" ");
				return new DimmLocation(Integer.parseInt(socketLocator[1]), Integer.parseInt(socketLocator[3]));
			}
		}

		public static IloMemoryDimm from(IPv4Address ip, String authData, JsonNode node)
				throws JsonMappingException, JsonProcessingException, Exception {

			JsonNode dimmNode = IloMemoryObject.getDimmNode(ip, Optional.of(authData), node);

			HpMemoryStatus status = HpMemoryStatus.get(JsonUtil.getSafeTextValueFromNode(dimmNode, "DIMMStatus").toUpperCase());
			DimmLocation loc = DimmLocation.from(JsonUtil.getSafeTextValueFromNode(dimmNode, "SocketLocator"));
			DimmTechnology tech = DimmTechnology
					.valueOf(JsonUtil.getSafeTextValueFromNode(dimmNode, "DIMMTechnology").toUpperCase());
			DimmGeneration gen = DimmGeneration
					.valueOf(JsonUtil.getSafeTextValueFromNode(dimmNode, "DIMMType").toUpperCase());
			HpMemoryErrorCorrection ecc = HpMemoryErrorCorrection
					.get(JsonUtil.getSafeTextValueFromNode(dimmNode, "ErrorCorrection").toUpperCase());
			HpMemoryType hpMem = HpMemoryType
					.valueOf(JsonUtil.getSafeTextValueFromNode(dimmNode, "HPMemoryType").toUpperCase());

			String id = JsonUtil.getSafeTextValueFromNode(dimmNode, "id");
			String name = JsonUtil.getSafeTextValueFromNode(dimmNode, "Name");
			String manufact = JsonUtil.getSafeTextValueFromNode(dimmNode, "Manufacturer");
			String part = JsonUtil.getSafeTextValueFromNode(dimmNode, "PartNumber");

			int rank = JsonUtil.getSafeIntValueFromNode(dimmNode, "Rank");
			int size = JsonUtil.getSafeIntValueFromNode(dimmNode, "SizeMB");
			int maxFreq = JsonUtil.getSafeIntValueFromNode(dimmNode, "MaximumFrequencyMHz");
			int widthData = JsonUtil.getSafeIntValueFromNode(dimmNode, "DataWidth");
			int widthTotal = JsonUtil.getSafeIntValueFromNode(dimmNode, "TotalWidth");
			int minVoltage = JsonUtil.getSafeIntValueFromNode(dimmNode, "MinimumVoltageVoltsX10");

			return IloMemoryDimm.builder().dataWidth(widthData).ecc(ecc).generation(gen).hpMemoryType(hpMem).id(id)
					.location(loc).manufacturer(manufact).maximumFrequency(maxFreq).minimumVoltageVolts(minVoltage)
					.name(name).partNumber(part).rank(rank).size(size).status(status).totalWidth(widthTotal).type(tech)
					.build();
		}

		@Override
		public void update(IPv4Address ip, String authData) {
			JsonNode dimmNode;
			try {
				dimmNode = getDimmNode(ip, Optional.of(authData));
				HpMemoryStatus stat = HpMemoryStatus
						.get(JsonUtil.getSafeTextValueFromNode(dimmNode, "DIMMStatus").toUpperCase());
				lastUpdateTime = System.currentTimeMillis();
				this.setStatus(stat);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public int getTimeBetweenUpdates() {
			return 300;
		}

		private JsonNode getDimmNode(IPv4Address iloAddress, Optional<String> auth) throws Exception {
			return getJsonNode(iloAddress, auth, "https://" + iloAddress.toString() + "/rest/v1/Systems/1/Memory/" + this.name);
		}

	}

	List<IloMemoryDimm> dimms;
	long lastUpdateTime;

	public static IloMemoryObject from(IPv4Address ip, String authData) throws Exception {

		JsonNode memoryNode = getMemoryNode(ip, Optional.of(authData));
		JsonNode memoryLinks = memoryNode.path("links").path("Member");
		List<IloMemoryDimm> dimms = new LinkedList<>();
		for (JsonNode node : memoryLinks) {
			try {
				dimms.add(IloMemoryDimm.from(ip, authData, node));
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		return IloMemoryObject.builder().dimms(dimms).build();

	}

	@Override
	public void update(IPv4Address ip, String authData) {
		for (IloMemoryDimm dimm : dimms) {
			try {
				if (dimm != null && dimm.canUpdate()) {
					dimm.update(ip, authData);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		this.setLastUpdateTime(System.currentTimeMillis());
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 300;
	}

	private static JsonNode getJsonNode(IPv4Address iloAddress, Optional<String> auth, String address)
			throws Exception {
		return JSON_MAPPER.readTree(NetworkUtil.fetchDataFromEndpoint(address, auth));
	}

	private static JsonNode getDimmNode(IPv4Address iloAddress, Optional<String> auth, JsonNode dimmNode)
			throws Exception {
		return getJsonNode(iloAddress, auth, "https://" + iloAddress.toString() + dimmNode.path("href").asText());
	}

	private static JsonNode getMemoryNode(IPv4Address iloAddress, Optional<String> auth) throws Exception {
		return getJsonNode(iloAddress, auth, "https://" + iloAddress.toString() + "/rest/v1/Systems/1/Memory");
	}

}
