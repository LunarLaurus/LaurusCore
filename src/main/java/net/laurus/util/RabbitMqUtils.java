package net.laurus.util;

import static net.laurus.Constant.JSON_MAPPER;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import lombok.experimental.UtilityClass;

/**
 * Utility class for handling RabbitMQ message payloads.
 * Provides serialization, compression, and decompression utilities.
 */
@UtilityClass
public final class RabbitMqUtils {

    /**
     * Prepares a payload for sending by serializing and optionally compressing it.
     *
     * @param data The object to be serialized.
     * @param compress Whether to compress the serialized data.
     * @return A byte array ready to be sent.
     * @throws IOException If serialization or compression fails.
     */
    public static byte[] preparePayload(Object data, boolean compress) throws IOException {
        byte[] serializedData = serializeToJson(data);
        return compress ? compress(serializedData) : serializedData;
    }

    /**
     * Serializes an object to JSON format.
     *
     * @param data The object to be serialized.
     * @return A byte array containing the JSON representation of the object.
     * @throws IOException If serialization fails.
     */
    private static byte[] serializeToJson(Object data) throws IOException {
        return JSON_MAPPER.writeValueAsBytes(data);
    }

    /**
     * Compresses a byte array using GZIP.
     *
     * @param data The data to be compressed.
     * @return A compressed byte array.
     * @throws IOException If compression fails.
     */
    private static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(data);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Processes an incoming payload by optionally decompressing and deserializing it.
     *
     * @param payload The received payload as a byte array.
     * @param clazz The class type to deserialize into.
     * @param <T> The type of the resulting object.
     * @return An object of type T.
     * @throws IOException If decompression or deserialization fails.
     */
    public static <T> T receivePayload(byte[] payload, Class<T> clazz) throws IOException {
        byte[] processedData = isCompressed(payload) ? decompress(payload) : payload;
        return deserializeFromJson(processedData, clazz);
    }

    /**
     * Decompresses a GZIP-compressed byte array.
     *
     * @param compressedData The compressed byte array to decompress.
     * @return A decompressed byte array.
     * @throws IOException If decompression fails.
     */
    private static byte[] decompress(byte[] compressedData) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(compressedData))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Deserializes a JSON byte array into an object of the specified type.
     *
     * @param data The JSON byte array to deserialize.
     * @param clazz The class type to deserialize into.
     * @param <T> The type of the resulting object.
     * @return An object of type T.
     * @throws IOException If deserialization fails.
     */
    private static <T> T deserializeFromJson(byte[] data, Class<T> clazz) throws IOException {
        return JSON_MAPPER.readValue(data, clazz);
    }

    /**
     * Checks if a given byte array is GZIP-compressed.
     *
     * @param data The byte array to check.
     * @return True if the byte array is compressed, false otherwise.
     */
    private static boolean isCompressed(byte[] data) {
        return (data != null && data.length > 2 && ((data[0] & 0xff) == 0x1f && (data[1] & 0xff) == 0x8b));
    }
    
}
