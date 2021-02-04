package io.kyberorg.yalsee.test.pageobjects.external;

/**
 * Page object for page https://www.foreca.fi, which is displayed when you visit to https://sää.fi .
 *
 * @since 2.5
 */
@SuppressWarnings("SpellCheckingInspection")
public class ForecaFi {

    private ForecaFi() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final String LOGO = ".logo a";
    public static final String LOGO_TITLE = "Foreca";
}
