package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CCDDefendant;
import uk.gov.hmcts.cmc.domain.models.defendants.TheirDetails;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
public class DefendantMapper implements Mapper<List<CCDCollectionElement<CCDDefendant>>, List<TheirDetails>> {

    private final TheirDetailsMapper theirDetailsMapper;

    @Autowired
    public DefendantMapper(TheirDetailsMapper theirDetailsMapper) {
        this.theirDetailsMapper = theirDetailsMapper;
    }

    @Override
    public List<CCDCollectionElement<CCDDefendant>> to(List<TheirDetails> theirDetails) {
        if (theirDetails == null) {
            return new ArrayList<>();
        }


        return theirDetails.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<TheirDetails> from(List<CCDCollectionElement<CCDDefendant>> defendant) {
        if (defendant == null) {
            return new ArrayList<>();
        }

        return defendant.stream()
                .filter(Objects::nonNull)
                .map(this::from)
                .collect(Collectors.toList());
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
