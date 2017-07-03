package ee.yals.services;

import ee.yals.result.AddResult;
import ee.yals.result.GetResult;
import ee.yals.storage.LinkStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class description
 *
 * @author Alexander Muravya (alexander.muravya@kuehne-nagel.com)
 * @since 0.0
 */
@Component
public class StdLinkService implements LinkService {

    @Autowired
    private LinkStorage storage;

    @Override
    public GetResult getLink(String ident) {
        String link = storage.find(ident);
        return link.equals(LinkStorage.LINK_NOT_FOUND) ? new GetResult.NotFound() : new GetResult.Success(link);
    }

    @Override
    public AddResult addNew(String ident, String link) {
        storage.save(ident, link);
        return new AddResult.Success();
    }
}
