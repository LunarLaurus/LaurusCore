package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ProLiantModels {

    @Getter
    @AllArgsConstructor
    public enum Generation {
    	UNKNOWN("Unknown"),
        GEN8("Gen 8"),
        GEN9("Gen 9"),
        GEN10("Gen 10"),
        GEN10_PLUS("Gen 10+"),
        GEN11("Gen 11");

        private final String displayName;
    }

    @Getter
    @AllArgsConstructor
    public enum ModelPrefix {
    	UNKNOWN("?"),
        BL("BL"),  // Blade
        DL("DL"),  // Density Line
        ML("ML"),  // Microserver
        SL("SL");  // Scalable Line

        private final String prefix;
    }

    @Getter
    @AllArgsConstructor
    public enum ModelNumber {
    	UNKNOWN(0),
        MODEL_20(20),
        MODEL_30(30),
        MODEL_60(60),
        MODEL_80(80),
        MODEL_110(110),
        MODEL_120(120),
        MODEL_150(150),
        MODEL_160(160),
        MODEL_180(180),
        MODEL_230(230),
        MODEL_250(250),
        MODEL_310(310),
        MODEL_320(320),
        MODEL_325(325),
        MODEL_350(350),
        MODEL_360(360),
        MODEL_380(380),
        MODEL_385(385),
        MODEL_460(460),
        MODEL_465(465),
        MODEL_560(560),
        MODEL_580(580),
        MODEL_660(660),
        MODEL_760(760),
        MODEL_2000(2000);
    	
    	int modelNumber;
    }

    @Getter
    @AllArgsConstructor
    public enum ProLiantModel {

    	UNKNOWN(Generation.UNKNOWN, ModelPrefix.UNKNOWN, ModelNumber.UNKNOWN, "?"),
        // Generation 8
        Gen8_DL160(Generation.GEN8, ModelPrefix.DL, ModelNumber.MODEL_160, ""),
        Gen8_DL320e(Generation.GEN8, ModelPrefix.DL, ModelNumber.MODEL_320, "e"),
        Gen8_DL360e(Generation.GEN8, ModelPrefix.DL, ModelNumber.MODEL_360, "e"),
        Gen8_DL380e(Generation.GEN8, ModelPrefix.DL, ModelNumber.MODEL_380, "e"),
        Gen8_DL360p(Generation.GEN8, ModelPrefix.DL, ModelNumber.MODEL_360, "p"),
        Gen8_DL380p(Generation.GEN8, ModelPrefix.DL, ModelNumber.MODEL_380, "p"),
        Gen8_DL560p(Generation.GEN8, ModelPrefix.DL, ModelNumber.MODEL_560, "p"),
        Gen8_DL580p(Generation.GEN8, ModelPrefix.DL, ModelNumber.MODEL_580, "p"),
        Gen8_DL760p(Generation.GEN8, ModelPrefix.DL, ModelNumber.MODEL_760, "p"),

        Gen8_BL460c(Generation.GEN8, ModelPrefix.BL, ModelNumber.MODEL_460, "c"),
        Gen8_BL465c(Generation.GEN8, ModelPrefix.BL, ModelNumber.MODEL_465, "c"),
        Gen8_BL660c(Generation.GEN8, ModelPrefix.BL, ModelNumber.MODEL_660, "c"),
        
        Gen8_ML110(Generation.GEN8, ModelPrefix.ML, ModelNumber.MODEL_110, ""),
        Gen8_ML310p(Generation.GEN8, ModelPrefix.ML, ModelNumber.MODEL_310, ""),
        Gen8_ML350p(Generation.GEN8, ModelPrefix.ML, ModelNumber.MODEL_350, ""),
        
        // Generation 9
        Gen9_DL20(Generation.GEN9, ModelPrefix.DL, ModelNumber.MODEL_20, ""),
        Gen9_DL60(Generation.GEN9, ModelPrefix.DL, ModelNumber.MODEL_60, ""),
        Gen9_DL80(Generation.GEN9, ModelPrefix.DL, ModelNumber.MODEL_80, ""),
        Gen9_DL120(Generation.GEN9, ModelPrefix.DL, ModelNumber.MODEL_120, ""),
        Gen9_DL160(Generation.GEN9, ModelPrefix.DL, ModelNumber.MODEL_160, ""),
        Gen9_DL180(Generation.GEN9, ModelPrefix.DL, ModelNumber.MODEL_180, ""),
        Gen9_DL380(Generation.GEN9, ModelPrefix.DL, ModelNumber.MODEL_380, ""),
        Gen9_DL560(Generation.GEN9, ModelPrefix.DL, ModelNumber.MODEL_560, ""),
        Gen9_DL580(Generation.GEN9, ModelPrefix.DL, ModelNumber.MODEL_580, ""),
        Gen9_BL460c(Generation.GEN9, ModelPrefix.BL, ModelNumber.MODEL_460, ""),
        Gen9_BL660c(Generation.GEN9, ModelPrefix.BL, ModelNumber.MODEL_660, "c"),
        Gen9_ML30(Generation.GEN9, ModelPrefix.ML, ModelNumber.MODEL_30, ""),
        Gen9_ML110(Generation.GEN9, ModelPrefix.ML, ModelNumber.MODEL_110, ""),
        Gen9_ML150(Generation.GEN9, ModelPrefix.ML, ModelNumber.MODEL_150, ""),
        Gen9_ML350(Generation.GEN9, ModelPrefix.ML, ModelNumber.MODEL_350, ""),
        
        // Generation 10
        Gen10_DL20(Generation.GEN10, ModelPrefix.DL, ModelNumber.MODEL_20, "e"),
        Gen10_DL325(Generation.GEN10, ModelPrefix.DL, ModelNumber.MODEL_325, "e"),      
        Gen10_DL360(Generation.GEN10, ModelPrefix.DL, ModelNumber.MODEL_360, ""),
        Gen10_DL380(Generation.GEN10, ModelPrefix.DL, ModelNumber.MODEL_380, ""),
        Gen10_DL385(Generation.GEN10, ModelPrefix.DL, ModelNumber.MODEL_385, ""),
        Gen10_DL560(Generation.GEN10, ModelPrefix.DL, ModelNumber.MODEL_560, ""),
        Gen10_DL580(Generation.GEN10, ModelPrefix.DL, ModelNumber.MODEL_580, ""),
        Gen10_DL760(Generation.GEN10, ModelPrefix.DL, ModelNumber.MODEL_760, ""),
        Gen10_BL460(Generation.GEN10, ModelPrefix.BL, ModelNumber.MODEL_460, ""),
        Gen10_ML30(Generation.GEN10, ModelPrefix.ML, ModelNumber.MODEL_30, ""),
        Gen10_ML110(Generation.GEN10, ModelPrefix.ML, ModelNumber.MODEL_110, ""),
        Gen10_ML350(Generation.GEN10, ModelPrefix.ML, ModelNumber.MODEL_350, ""),
        
        
        // Generation 10+
        Gen10Plus_DL360(Generation.GEN10_PLUS, ModelPrefix.DL, ModelNumber.MODEL_360, ""),
        Gen10Plus_DL380(Generation.GEN10_PLUS, ModelPrefix.DL, ModelNumber.MODEL_380, ""),
        Gen10Plus_DL560(Generation.GEN10_PLUS, ModelPrefix.DL, ModelNumber.MODEL_560, ""),
        Gen10Plus_DL580(Generation.GEN10_PLUS, ModelPrefix.DL, ModelNumber.MODEL_580, ""),

        // Generation 11
        Gen11_DL360(Generation.GEN11, ModelPrefix.DL, ModelNumber.MODEL_360, ""),
        Gen11_DL380(Generation.GEN11, ModelPrefix.DL, ModelNumber.MODEL_380, ""),
        Gen11_DL560(Generation.GEN11, ModelPrefix.DL, ModelNumber.MODEL_560, ""),
        Gen11_DL580(Generation.GEN11, ModelPrefix.DL, ModelNumber.MODEL_580, "");

        private final Generation generation;
        private final ModelPrefix prefix;
        private final ModelNumber number;
        private final String suffix;

        @Override
        public String toString() {
            return String.format("%s%s%s (%s)", prefix.getPrefix(), number.getModelNumber(), suffix, generation.getDisplayName());
        }
    }
    
}
