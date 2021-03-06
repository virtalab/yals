package io.kyberorg.yalsee.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import io.kyberorg.yalsee.utils.UrlExtraValidator;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static io.kyberorg.yalsee.utils.UrlExtraValidator.URL_MAX_SIZE;
import static io.kyberorg.yalsee.utils.UrlExtraValidator.URL_MIN_SIZE;

/**
 * Store Endpoint incoming JSON.
 *
 * @since 1.0
 */
@Data(staticConstructor = "create")
public class StoreRequestJson implements YalseeJson {
    @NotNull(message = "must be present")
    @Size(min = URL_MIN_SIZE, max = URL_MAX_SIZE)
    @URL(message = UrlExtraValidator.URL_NOT_VALID)
    @JsonProperty("link")
    private String link;

    /**
     * Creates {@link StoreRequestJson} with provided link.
     *
     * @param longLink field with long link to shorten
     * @return JSON which contains long link in {@link #link} param
     */
    public StoreRequestJson withLink(final String longLink) {
        this.link = longLink;
        return this;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
