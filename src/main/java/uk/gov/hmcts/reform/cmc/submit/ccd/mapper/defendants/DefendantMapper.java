package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CcdDefendant;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.Mapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
public class DefendantMapper implements Mapper<List<CcdCollectionElement<CcdDefendant>>, List<TheirDetails>> {

    private final TheirDetailsMapper theirDetailsMapper;

    @Autowired
    public DefendantMapper(TheirDetailsMapper theirDetailsMapper) {
        this.theirDetailsMapper = theirDetailsMapper;
    }

    @Override
    public List<CcdCollectionElement<CcdDefendant>> to(List<TheirDetails> theirDetails) {
        if (theirDetails == null) {
            return new ArrayList<>();
        }


        return theirDetails.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<TheirDetails> from(List<CcdCollectionElement<CcdDefendant>> defendant) {
        if (defendant == null) {
            return new ArrayList<>();
        }

        return defendant.stream()
                .filter(Objects::nonNull)
                .map(this::from)
                .collect(Collectors.toList());
    }


    public CcdCollectionElement<CcdDefendant> to(TheirDetails theirDetails) {
        requireNonNull(theirDetails, "theirDetails must not be null");

        CcdDefendant.CcdDefendantBuilder builder = CcdDefendant.builder();

        theirDetailsMapper.to(builder, theirDetails);

        return CcdCollectionElement.<CcdDefendant>builder()
            .value(builder.build())
            .id(theirDetails.getId())
            .build();
    }

    public TheirDetails from(CcdCollectionElement<CcdDefendant> defendant) {

        return theirDetailsMapper.from(defendant);
    }

}
