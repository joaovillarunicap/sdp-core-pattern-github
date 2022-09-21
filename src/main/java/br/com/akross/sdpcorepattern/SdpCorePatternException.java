package br.com.akross.sdpcorepattern;

import lombok.Getter;

@Getter
public class SdpCorePatternException extends IllegalArgumentException {

    private final RegexValidator validator;

    public SdpCorePatternException(RegexValidator validator) {
        this.validator = validator;
    }

    public SdpCorePatternException(IllegalAccessException e) {
        super(e);
        validator = null;
    }
}