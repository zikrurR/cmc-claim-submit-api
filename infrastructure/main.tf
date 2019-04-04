provider "azurerm" {}

locals {
  ase_name       = "${data.terraform_remote_state.core_apps_compute.ase_name[0]}"
  s2s_url        = "http://rpe-service-auth-provider-${local.local_env}.service.${local.local_ase}.internal"
  ccd_url        = "http://ccd-data-store-api-${var.env}.service.${local.local_ase}.internal"
  cmc_vault_name = "${var.raw_product}-${var.env}"
  
}

module "cmc-claim-submit-api" {
  source              = "git@github.com:hmcts/moj-module-webapp?ref=master"
  product             = "${var.product}-${var.component}"
  location            = "${var.location_app}"
  env                 = "${var.env}"
  ilbIp               = "${var.ilbIp}"
  subscription        = "${var.subscription}"
  capacity            = "${var.capacity}"
  common_tags         = "${var.common_tags}"

  app_settings = {
    LOGBACK_REQUIRE_ALERT_LEVEL = "false"
    LOGBACK_REQUIRE_ERROR_CODE  = "false"
  }
}

data "azurerm_key_vault" "cmc_key_vault" {
  name = "${local.cmc_vault_name}"
  resource_group_name = "${local.cmc_vault_name}"
}

data "azurerm_key_vault_secret" "s2s_secret" {
  name = "claim-store-s2s-secret"
  vault_uri = "${data.azurerm_key_vault.cmc_key_vault.vault_uri}"
}
