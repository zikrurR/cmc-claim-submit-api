package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CCDDefendant;
import uk.gov.hmcts.cmc.domain.models.defendants.TheirDetails;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.TheirDetailsMapper;

import static java.util.Objects.requireNonNull;

@Component
public class DefendantMapper {

    private final TheirDetailsMapper theirDetailsMapper;

    @Autowired
    public DefendantMapper(
        TheirDetailsMapper theirDetailsMapper
    ) {
        this.theirDetailsMapper = theirDetailsMapper;
    }

    public CCDCollectionElement<CCDDefendant> to(TheirDetails theirDetails) {
        requireNonNull(theirDetails, "theirDetails must not be null");

        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();

        theirDetailsMapper.to(builder, theirDetails);

        return CCDCollectionElement.<CCDDefendant>builder()
            .value(builder.build())
            .id(theirDetails.getId())
            .build();
    }

    public TheirDetails from(CCDCollectionElement<CCDDefendant> defendant) {

        return theirDetailsMapper.from(defendant);
    }

}
