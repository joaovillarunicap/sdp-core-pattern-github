package br.com.akross.sdpcorepattern;

import lombok.AllArgsConstructor;

import java.util.*;
import java.util.regex.Matcher;

@AllArgsConstructor
public enum SdpCorePatternTypeStrategy {
    STRING("java.lang.String") {
        @Override
        public List<RegexValidator> getRegexValidatorList(Pattern annotation) {
            List<RegexValidator> validators = new ArrayList<>();

            if (Objects.isNull(annotation)) {
                validators.add(RegexValidator.CHARACTER_NUMBERS_AND_SPECIAL_CHARS);
            } else {
                validators.add(annotation.value());
            }

            return validators;
        }

        @Override
        public void execute(Object value, List<RegexValidator> validatorList) throws SdpCorePatternException, IllegalAccessException {
            String attributeValue = (String) value;
            boolean result = hasSomeIncorrectValue(attributeValue, validatorList.get(0).getRegex());
            if (result) {
                throw new SdpCorePatternException(validatorList.get(0));
            }
        }

    }, MAP("java.util.Map") {
        @Override
        public List<RegexValidator> getRegexValidatorList(Pattern annotation) {
            List<RegexValidator> validators = new ArrayList<>();

            if (Objects.isNull(annotation)) {
                validators.add(RegexValidator.CHARACTER_NUMBERS_AND_SPECIAL_CHARS);
                validators.add(RegexValidator.CHARACTER_WITH_ACCENT_NUMBERS_AND_SPECIAL_CHARS);
            } else {
                validators.add(annotation.value());
                validators.add(annotation.secondValue());
            }

            return validators;
        }

        @Override
        public void execute(Object object, List<RegexValidator> validatorList) throws SdpCorePatternException, IllegalAccessException {
            Map<String, String> map = (Map) object;

            map.forEach((key, value) -> {
                RegexValidator validator = validatorList.get(0);

                boolean keyBoolean = hasSomeIncorrectValue(key, validatorList.get(0).getRegex());
                boolean valueBoolean = hasSomeIncorrectValue(value, validatorList.get(1).getRegex());

                if (keyBoolean) {
                    validator = validatorList.get(0);
                } else if (valueBoolean) {
                    validator = validatorList.get(1);
                }

                if (keyBoolean || valueBoolean) {
                    throw new SdpCorePatternException(validator);
                }
            });
        }
    };

    private final String typeName;

    public static SdpCorePatternTypeStrategy getFromTypeName(String name) {
        return Arrays.stream(SdpCorePatternTypeStrategy.values())
                .filter(f -> Objects.equals(f.typeName, name))
                .findFirst()
                .orElse(null);
    }

    private static boolean hasSomeIncorrectValue(String value, String regex) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        Matcher m = pattern.matcher(value);
        return !m.matches();
    }

    public abstract List<RegexValidator> getRegexValidatorList(Pattern annotation);

    public abstract void execute(Object value, List<RegexValidator> validatorList) throws SdpCorePatternException, IllegalAccessException;
}
