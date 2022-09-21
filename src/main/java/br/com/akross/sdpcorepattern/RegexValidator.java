package br.com.akross.sdpcorepattern;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegexValidator {
    CHARACTER_NUMBERS_AND_SPECIAL_CHARS(Constants.REGEX_CHARACTER_NUMBERS_AND_SPECIAL_CHARS,
            MessageBundle.getMessage("exception.invalid.characters.allowed")),
    CHARACTER_WITH_ACCENT_NUMBERS_AND_SPECIAL_CHARS(Constants.REGEX_CHARACTER_WITH_ACCENT_NUMBERS_AND_SPECIAL_CHARS,
            MessageBundle.getMessage("exception.invalid.accented.characters.allowed")),
    NULL_OR_EMPTY("",
            MessageBundle.getMessage("exception.field.empty.or.null")),
    DEFAULT("", "");

    private final String regex;
    private final String errorMessage;
}
