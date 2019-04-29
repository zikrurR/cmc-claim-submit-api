package uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders;

import java.util.Map;

public interface Builder<T> {

    public Map<String, Object> buildMap();

    public T build();

}
