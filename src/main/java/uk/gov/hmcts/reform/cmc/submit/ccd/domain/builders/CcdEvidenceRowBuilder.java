package uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdEvidenceRow;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdEvidenceType;

import java.util.HashMap;
import java.util.Map;

public class CcdEvidenceRowBuilder implements Builder<CcdEvidenceRow> {
    private CcdEvidenceType type;
    private String description;

    private Map<String, Object> propertiesMap = new HashMap<>();

    public static CcdEvidenceRowBuilder builder() {
        return new CcdEvidenceRowBuilder();
    }

    private CcdEvidenceRowBuilder() {
    }

    @Override
    public CcdEvidenceRow build() {
        return new CcdEvidenceRow(type, description);
    }

    public Map<String, Object> buildMap() {
        return propertiesMap;
    }

    public CcdEvidenceRowBuilder type(CcdEvidenceType type) {
        this.type = type;
        propertiesMap.put("type", type);
        return this;
    }

    public CcdEvidenceRowBuilder description(String description) {
        this.description = description;
        propertiesMap.put("description", description);
        return this;
    }

}
