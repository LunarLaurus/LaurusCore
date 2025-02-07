package net.laurus.data.enums.system;

/**
 * Enum representing different DIMM (Dual Inline Memory Module) technologies.
 */
public enum DimmTechnology {

    /** Bursted EDO (Extended Data Out) memory */
    BURSTEDO,

    /** Fast Page Mode memory */
    FASTPAGE,

    /** Synchronous memory */
    SYNCHRONOUS,

    /** Extended Data Out memory */
    EDO,

    /** Load-Reduced DIMM memory */
    LRDIMM,

    /** Rambus DRAM memory */
    RDRAM,

    /** Registered DIMM memory */
    RDIMM,

    /** Unbuffered DIMM memory */
    UDIMM,

    /** Non-Volatile DIMM memory */
    NVDIMM,

    /** Registered Non-Volatile DIMM memory */
    RNVDIMM,

    /** Load-Reduced Non-Volatile DIMM memory */
    LRNVDIMM,

    /** Unknown DIMM technology */
    UNKNOWN;
}
