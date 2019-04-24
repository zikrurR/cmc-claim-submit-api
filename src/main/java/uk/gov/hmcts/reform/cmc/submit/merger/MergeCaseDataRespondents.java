package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.CcdPartyType;
import uk.gov.hmcts.cmc.ccd.domain.CcdRespondent;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.AddressMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.CompanyDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.OrganisationDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.SoleTraderDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
class MergeCaseDataRespondents implements MergeCaseDataDecorator {

    private final AddressMapper addressMapper;

    @Autowired
    public MergeCaseDataRespondents(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public void merge(CcdCase ccdCase, ClaimInput claim) {

        ccdCase.setRespondents(to(claim.getDefendants()));

    }

    private List<CcdCollectionElement<CcdRespondent>> to(List<TheirDetails> theirDetails) {
        if (theirDetails == null) {
            return new ArrayList<>();
        }

        return theirDetails.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    public CcdCollectionElement<CcdRespondent> to(TheirDetails theirDetails) {
        requireNonNull(theirDetails, "theirDetails must not be null");

        CcdRespondent.CcdRespondentBuilder builder = CcdRespondent.builder();

        theirDetails(builder, theirDetails);

        return CcdCollectionElement.<CcdRespondent>builder()
            .value(builder.build())
            .id(theirDetails.getId())
            .build();
    }

    public void theirDetails(CcdRespondent.CcdRespondentBuilder builder, TheirDetails theirDetails) {

        if (theirDetails instanceof IndividualDetails) {
            builder.claimantProvidedType(CcdPartyType.INDIVIDUAL);
            IndividualDetails individual = (IndividualDetails) theirDetails;
            individualDetails(individual, builder);
        } else if (theirDetails instanceof CompanyDetails) {
            builder.claimantProvidedType(CcdPartyType.COMPANY);
            CompanyDetails company = (CompanyDetails) theirDetails;
            companyDetails(company, builder);
        } else if (theirDetails instanceof OrganisationDetails) {
            builder.claimantProvidedType(CcdPartyType.ORGANISATION);
            OrganisationDetails organisation = (OrganisationDetails) theirDetails;
            organisationDetails(organisation, builder);
        } else if (theirDetails instanceof SoleTraderDetails) {
            builder.claimantProvidedType(CcdPartyType.SOLE_TRADER);
            SoleTraderDetails soleTrader = (SoleTraderDetails) theirDetails;
            soleTraderDetails(soleTrader, builder);
        }
    }

    public void individualDetails(IndividualDetails individual, CcdRespondent.CcdRespondentBuilder builder) {

        builder.claimantProvidedServiceAddress(addressMapper.to(individual.getServiceAddress()));
        builder.claimantProvidedDateOfBirth(individual.getDateOfBirth());
        builder.claimantProvidedEmail(individual.getEmail());
        builder.claimantProvidedName(individual.getName());
        builder.claimantProvidedAddress(addressMapper.to(individual.getAddress()));

        representative(individual.getRepresentative(), builder);
    }

    public void companyDetails(CompanyDetails company, CcdRespondent.CcdRespondentBuilder builder) {

        builder.claimantProvidedEmail(company.getEmail());
        builder.claimantProvidedContactPerson(company.getContactPerson());
        builder.claimantProvidedServiceAddress(addressMapper.to(company.getServiceAddress()));
        builder.claimantProvidedName(company.getName());
        builder.claimantProvidedAddress(addressMapper.to(company.getAddress()));

        representative(company.getRepresentative(), builder);

    }

    public void organisationDetails(OrganisationDetails organisation, CcdRespondent.CcdRespondentBuilder builder) {
        if (organisation == null) {
            return;
        }

        builder.claimantProvidedServiceAddress(addressMapper.to(organisation.getServiceAddress()));
        representative(organisation.getRepresentative(), builder);

        builder.claimantProvidedContactPerson(organisation.getContactPerson());
        builder.claimantProvidedCompaniesHouseNumber(organisation.getCompaniesHouseNumber());
        builder.claimantProvidedEmail(organisation.getEmail());
        builder.claimantProvidedName(organisation.getName());
        builder.claimantProvidedAddress(addressMapper.to(organisation.getAddress()));

    }

    public void soleTraderDetails(SoleTraderDetails soleTrader, CcdRespondent.CcdRespondentBuilder builder) {
        if (soleTrader == null) {
            return;
        }

        builder.claimantProvidedTitle(soleTrader.getTitle());
        builder.claimantProvidedBusinessName(soleTrader.getBusinessName());

        representative(soleTrader.getRepresentative(), builder);
        builder.claimantProvidedEmail(soleTrader.getEmail());
        builder.claimantProvidedServiceAddress(addressMapper.to(soleTrader.getServiceAddress()));

        builder.claimantProvidedName(soleTrader.getName());
        builder.claimantProvidedAddress(addressMapper.to(soleTrader.getAddress()));

    }

    public void representative(Representative representative, CcdRespondent.CcdRespondentBuilder builder) {
        if (representative == null) {
            return;
        }

        defendantContactDetails(representative.getOrganisationContactDetails(), builder);

        builder.claimantProvidedRepresentativeOrganisationName(representative.getOrganisationName());
        builder.claimantProvidedRepresentativeOrganisationAddress(addressMapper.to(representative.getOrganisationAddress()));
    }

    public void defendantContactDetails(ContactDetails contactDetails, CcdRespondent.CcdRespondentBuilder builder) {

        builder.claimantProvidedRepresentativeOrganisationEmail(contactDetails.getEmail());
        builder.claimantProvidedRepresentativeOrganisationPhone(contactDetails.getPhone());
        builder.claimantProvidedRepresentativeOrganisationDxAddress(contactDetails.getDxAddress());
    }

}
