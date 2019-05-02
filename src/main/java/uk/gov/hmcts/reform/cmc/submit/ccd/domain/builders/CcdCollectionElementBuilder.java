package uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCollectionElement;

import java.util.HashMap;
import java.util.Map;

public class CcdCollectionElementBuilder<E> implements Builder<CcdCollectionElement<E>> {
    private String id;
    private Builder<E> value;

    private Map<String, Object> propertiesMap = new HashMap<>();

    public static <E>  CcdCollectionElementBuilder<E> builder() {
        return new CcdCollectionElementBuilder<>();
    }

    private CcdCollectionElementBuilder() {
    }

    @Override
    public CcdCollectionElement<E> build() {
        CcdCollectionElement<E> ccdCollectionElement = new CcdCollectionElement<>();
        ccdCollectionElement.setId(id);
        if (value != null) {
            ccdCollectionElement.setValue(value.build());
        }

        return ccdCollectionElement;
    }

    @Override
    public Map<String, Object> buildMap() {
        if (value != null) {
            propertiesMap.put("value", value.buildMap());
        }
        return propertiesMap;
    }

    public CcdCollectionElementBuilder<E> id(String id) {
        this.id = id;
        propertiesMap.put("id", id);
        return this;
    }

    public CcdCollectionElementBuilder<E> value(Builder<E> value) {
        this.value = value;
        return this;
    }
}
