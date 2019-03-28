package uk.gov.hmcts.reform.cmc.submit.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.gov.hmcts.cmc.domain.models.ClaimData;
import uk.gov.hmcts.reform.cmc.submit.services.ClaimService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api
@RestController
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimService claimService;

    @Autowired
    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping("/{externalId}")
    @ApiOperation("Fetch claim for given external id")
    public ClaimData getByExternalId(@PathVariable("externalId") String externalId,
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authorisation) {

//        ClaimData claim;
//        try {
//            claim = claimService.getClaimByReference(externalId, authorisation);
//        } catch (NotFoundException e) {
//            claim = claimService.getClaimByExternalId(externalId, authorisation)
//        }

        return null;

    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("Creates a new claim")
    public ClaimData save(
        @Valid @NotNull @RequestBody ClaimData claimData,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorisation) {
        return claimService.createNewCase(claimData, authorisation);
    }

}
