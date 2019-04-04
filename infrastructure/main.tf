provider "azurerm" {}

locals {
  local_env   = "${(var.env == "preview" || var.env == "spreview") ? (var.env == "preview" ) ? "aat" : "saat" : var.env}"
  ase_name    = "${data.terraform_remote_state.core_apps_compute.ase_name[0]}"
  s2s_url     = "http://rpe-service-auth-provider-${local.local_env}.service.${local.local_ase}.internal"
  ccd_url     = "http://ccd-data-store-api-${local.local_env}.service.${local.local_ase}.internal"
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

data "azurerm_key_vault_secret" "s2s_secret" {
  name = "claim-store-s2s-secret"
  vault_uri = "${data.azurerm_key_vault.cmc_key_vault.vault_uri}"
}
