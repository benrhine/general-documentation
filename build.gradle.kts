import ch.nomisp.confluence.publisher.PublishToConfluenceTask
import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.sahli.asciidoc.confluence.publisher.client.OrphanRemovalStrategy
import se.bjurr.gitchangelog.plugin.gradle.GitChangelogTask
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.IsoFields

plugins {
    id("java")
    // # tag::asciidocImport[]
    id("org.asciidoctor.jvm.convert")       version "4.0.4"
    id("org.asciidoctor.jvm.pdf")           version "4.0.4"
    // # end::asciidocImport[]
    // https://plugins.gradle.org/plugin/ch.nomisp.confluence.publisher
    // https://nomisp.github.io/confluence-publisher-plugin/index/user-guide.html#_the_confluence_publisher_plugin
    // # tag::publishToConfluenceImport[]
    id("ch.nomisp.confluence.publisher")    version "0.7.0"
    // # end::publishToConfluenceImport[]
    id("se.bjurr.gitchangelog.git-changelog-gradle-plugin") version "3.1.1"
}

//var confluenceUser:                                 String? = null
//var confluenceAuthorization:                        String? = null
//var confluenceUrl:                                  String? = null
//var confluenceApi:                                  String? = null
//
//var confluenceDpdTeamPageId:                        String? = null
//var confluenceDpdTeamBusinessPageId:                String? = null
//var confluenceDpdTeamEngineeringPageId:             String? = null
//var confluenceDpdTeamPracticesPageId:               String? = null
//var confluenceDpdTeamProjectsPageId:                String? = null
//var confluenceDpdTeamPropertiesPageId:              String? = null
//var confluenceDpdTeamDeploymentPageId:              String? = null
//var confluenceDpdTeamSupportPageId:                 String? = null
//var confluenceDpdTeamRunBooksPageId:                String? = null
//var confluenceDpdTeamDisasterRecoveryPageId:        String? = null
//var confluenceDpdTeamHowToPageId:                   String? = null
//var confluenceDpdTeamTutorialPageId:                String? = null
//var confluenceDpdTeamToolingPageId:                 String? = null
//var confluenceDpcTeamMiscPageId:                    String? = null
//
//var confluenceDpdTeamProjectRepo1PageId:            String? = null
//var confluenceDpdTeamProjectRepo1AdrPageId:         String? = null
//var confluenceDpdTeamProjectRepo1TechSpecPageId:    String? = null
//var confluenceDpdTeamProjectRepo1ChangelogPageId:   String? = null
//var confluenceDpdTeamProjectRepo1Sub1PageId:        String? = null
//var confluenceDpdTeamProjectRepo1Sub2PageId:        String? = null
//var confluenceDpdTeamProjectRepo1Sub3PageId:        String? = null
//var confluenceDpdTeamProjectRepo1Sub4PageId:        String? = null
//
//// Hosted Confluence Way
//// ./gradlew clean publishToConfluence --stacktrace -Dpat=PAT-VALUE
//val confluencePat: String? = System.getProperty("pat") ?: System.getenv("CONFLUENCE_PAT")
//
//if (confluencePat != null) {
//    confluenceUser                                  = ""
//    confluenceAuthorization                         = confluencePat
//    confluenceUrl                                   = System.getenv("CONFLUENCE_URL")
//    confluenceApi                                   = "v1"
//    confluenceDpdTeamPageId                         = System.getenv("TEAM_PAGE_ID") ?: "254610795"
//    confluenceDpdTeamBusinessPageId                 = System.getenv("BUSINESS_PAGE_ID")
//    confluenceDpdTeamEngineeringPageId              = System.getenv("ENGINEERING_PAGE_ID")
//    confluenceDpdTeamPracticesPageId                = System.getenv("PRACTICES_PAGE_ID")
//    confluenceDpdTeamProjectsPageId                 = System.getenv("PROJECTS_PAGE_ID")
//    confluenceDpdTeamPropertiesPageId               = System.getenv("PROPERTIES_PAGE_ID")
//    confluenceDpdTeamDeploymentPageId               = System.getenv("DEPLOYMENT_PAGE_ID")
//    confluenceDpdTeamSupportPageId                  = System.getenv("SUPPORT_PAGE_ID")
//    confluenceDpdTeamRunBooksPageId                 = System.getenv("RUN_BOOKS_PAGE_ID")
//    confluenceDpdTeamDisasterRecoveryPageId         = System.getenv("DISASTER_RECOVERY_PAGE_ID")
//    confluenceDpdTeamHowToPageId                    = System.getenv("HOW_TO_PAGE_ID")
//    confluenceDpdTeamTutorialPageId                 = System.getenv("TUTORIALS_PAGE_ID")
//    confluenceDpdTeamToolingPageId                  = System.getenv("TOOLING_PAGE_ID")
//    confluenceDpcTeamMiscPageId                     = System.getenv("MISC_PAGE_ID")
//
//    // THESE VALUES MUST BE MANUALLY SET "AFTER" THE PAGES HAVE BEEN CREATED
//    // THESE ARE PROJECT SPECIFIC VALUES
//    confluenceDpdTeamProjectRepo1PageId             = ""
//    confluenceDpdTeamProjectRepo1AdrPageId          = ""
//    confluenceDpdTeamProjectRepo1TechSpecPageId     = ""
//    confluenceDpdTeamProjectRepo1ChangelogPageId    = ""
//    confluenceDpdTeamProjectRepo1Sub1PageId         = ""
//    confluenceDpdTeamProjectRepo1Sub2PageId         = ""
//    confluenceDpdTeamProjectRepo1Sub3PageId         = ""
//    confluenceDpdTeamProjectRepo1Sub4PageId         = ""
//}
//
//// Cloud Confluence Way
//// ./gradlew clean publishToConfluence --stacktrace -Dtoken=TOKEN-VALUE
//val confluenceToken: String? = System.getProperty("token") ?: System.getenv("CONFLUENCE_TOKEN")
//
//if (confluenceToken != null) {
//    if (confluencePat != null) {
//        println("WARNING!!! Both Confluence PAT and Token have been set. Token will be preferred.")
//        println("Publishing WILL BE configured for Confluence Cloud (v2).")
//        println("If you wish to publish to legacy hosted confluence please ONLY provide the PAT.")
//    }
//
//    confluenceUser                                  = System.getenv("CONFLUENCE_USER")
//    confluenceAuthorization                         = confluenceToken
//    confluenceUrl                                   = System.getenv("CONFLUENCE_URL")
//    confluenceApi                                   = "v2"
//    confluenceDpdTeamPageId                         = System.getenv("TEAM_PAGE_ID")
//    confluenceDpdTeamBusinessPageId                 = System.getenv("BUSINESS_PAGE_ID")
//    confluenceDpdTeamEngineeringPageId              = System.getenv("ENGINEERING_PAGE_ID")
//    confluenceDpdTeamPracticesPageId                = System.getenv("PRACTICES_PAGE_ID")
//    confluenceDpdTeamProjectsPageId                 = System.getenv("PROJECTS_PAGE_ID")
//    confluenceDpdTeamPropertiesPageId               = System.getenv("PROPERTIES_PAGE_ID")
//    confluenceDpdTeamDeploymentPageId               = System.getenv("DEPLOYMENT_PAGE_ID")
//    confluenceDpdTeamSupportPageId                  = System.getenv("SUPPORT_PAGE_ID")
//    confluenceDpdTeamRunBooksPageId                 = System.getenv("RUN_BOOKS_PAGE_ID")
//    confluenceDpdTeamDisasterRecoveryPageId         = System.getenv("DISASTER_RECOVERY_PAGE_ID")
//    confluenceDpdTeamHowToPageId                    = System.getenv("HOW_TO_PAGE_ID")
//    confluenceDpdTeamTutorialPageId                 = System.getenv("TUTORIALS_PAGE_ID")
//    confluenceDpdTeamToolingPageId                  = System.getenv("TOOLING_PAGE_ID")
//    confluenceDpcTeamMiscPageId                     = System.getenv("MISC_PAGE_ID")
//
//    // THESE VALUES MUST BE MANUALLY SET "AFTER" THE PAGES HAVE BEEN CREATED
//    // THESE ARE PROJECT SPECIFIC VALUES
//    confluenceDpdTeamProjectRepo1PageId             = "5393678510"
//    confluenceDpdTeamProjectRepo1AdrPageId          = "5393842305"
//    confluenceDpdTeamProjectRepo1TechSpecPageId     = "5393285218"
//    confluenceDpdTeamProjectRepo1ChangelogPageId    = "5393645703"
//    confluenceDpdTeamProjectRepo1Sub1PageId         = "5393514582"
//    confluenceDpdTeamProjectRepo1Sub2PageId         = "5393252470"
//    confluenceDpdTeamProjectRepo1Sub3PageId         = "5393776747"
//    confluenceDpdTeamProjectRepo1Sub4PageId         = "5393449038"
//}
//
//if ((confluencePat == null) && (confluenceToken == null)) {
//    println("Confluence authentication not received!!! This may indicate a LOCAL system run - Documentation will not be published.")
//}

// Date components
val today                                           = getDate()
val year                                            = getCurrentYear()
val month                                           = getCurrentMonth()
val week                                            = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)

// Confluence values
val srcDirAbs                                       = file(projectDir).absolutePath
val includeDirAbs                                   = file("$projectDir/_includes").absolutePath
var confluenceSpaceNameFull                         = "Confluence Space"
var confluenceSpaceNameAbv                          = "CS"                 // Global Data Product
var organization1NameFull                           = "Rhine Consulting"
var organization1NameAbv                            = "RC"                 // Integrated Data Services
var team1NameFull                                   = "Ben Rhine"
val team1NameAbv                                    = "BR"                 // Digital Product Delivery
val projectName                                     = project.name          // Repo name
val confluenceSpace                                 = "CS"

val gitUserName                                     = System.getenv("GIT_USER_NAME")  ?: "git-user-name"
val gitUserEmail                                    = System.getenv("GIT_USER_EMAIL") ?: "git-user-email"
val gitBuildNumber:                                 String? = System.getenv("GIT_BUILD_NUM")
val gitBuildAttempt:                                String? = System.getenv("GIT_BUILD_ATTEMPT")
val confluenceVersionMessage                        = System.getenv("GIT_COMMIT_MSG") ?: "git-commit-message"

// # tag::confluencePublisher3[]
val asciiDocAttributes = mapOf(
    // This value will ONLY set correctly for AsciiDoc runs, it WILL NOT set correctly when publishing to Confluence
    // so files MUST have :toc: in them
    // "toc" to "",
    "deploy" to "",
    "sourcedir"                 to srcDirAbs,
    "includedir"                to includeDirAbs,
    "author"                    to gitUserName,             // This value will only be visible in generated doc, Confluence derives author from PAT
    "project-name"              to projectName,         // Dynamically set project name
    "revdate"                   to today,               // Update last updated value
    "space-name-full"           to confluenceSpaceNameFull,
    "space-name-abv"            to confluenceSpaceNameAbv.lowercase(),
    "space-name-abv-upper"      to confluenceSpaceNameAbv,
    "org-name-1-full"           to organization1NameFull,
    "org-name-1-abv"            to organization1NameAbv.lowercase(),
    "org-name-1-abv-upper"      to organization1NameAbv,
    "org-name-1-link"           to "",
    "team-name-1-full"          to team1NameFull,
    "team-name-1-abv"           to team1NameAbv.lowercase(),
    "team-name-1-abv-upper"     to team1NameAbv,
    "project-name-1-full"       to projectName,
    "project-name-1-abv"        to projectName.lowercase(),
    "project-name-1-abv-upper"  to projectName.uppercase(),
    "project-link"              to "https://github.com/benrhine/general-documentation",
    "project-repo"              to "https://github.com/benrhine/general-documentation",
    "fl-home-link"              to "https://doc.benrhine.com",
    "fl-architecture-link"      to "https://doc.benrhine.com/architecture",
    "fl-business-link"          to "https://doc.benrhine.com/business",
    "fl-engineering-link"       to "https://doc.benrhine.com/engineering",
    "fl-archive-link"           to "https://doc.benrhine.com/archive",
    "sl-best-practices-link"    to "https://doc.benrhine.com/engineering/best-practices",
    "sl-cloud-link"             to "https://doc.benrhine.com/engineering/cloud",
    "sl-deployment-link"        to "https://doc.benrhine.com/engineering/deployment",
    "sl-deployment-link-001"    to "https://doc.benrhine.com/engineering/deployment/deployment",
    "sl-deployment-link-002"    to "https://doc.benrhine.com/engineering/deployment/deployment-checklists",
    "sl-deployment-link-003"    to "https://doc.benrhine.com/engineering/deployment/deployment-lyp-example-checklist",
    "sl-development-link"       to "https://doc.benrhine.com/engineering/development",
    "sl-development-build-link"       to "https://doc.benrhine.com/engineering/development/build/",
    "sl-development-build-link-001"       to "https://doc.benrhine.com/engineering/development/build/build",
    "sl-development-build-link-002"       to "https://doc.benrhine.com/engineering/development/build/build-cd-codepipeline",
    "sl-development-build-link-003"       to "https://doc.benrhine.com/engineering/development/build/build-ci-bitbucket-pipelines",
    "sl-development-build-link-004"       to "https://doc.benrhine.com/engineering/development/build/build-ci-codebuild",
    "sl-development-build-link-005"       to "https://doc.benrhine.com/engineering/development/build/build-ci-codebuild-properties",
    "sl-development-build-link-006"       to "https://doc.benrhine.com/engineering/development/build/build-ci-github-actions",
    "sl-development-build-link-007"       to "https://doc.benrhine.com/engineering/development/build/build-ci-gitlab-pipelines",
    "sl-development-build-link-008"       to "https://doc.benrhine.com/engineering/development/build/build-ci-jenkins",
    "sl-development-build-link-009"       to "https://doc.benrhine.com/engineering/development/build/build-dependencies-updates",
    "sl-development-build-link-010"       to "https://doc.benrhine.com/engineering/development/build/build-gradle-artifact",
    "sl-development-build-link-011"       to "https://doc.benrhine.com/engineering/development/build/build-gradle-git-hooks",
    "sl-development-build-link-012"       to "https://doc.benrhine.com/engineering/development/build/build-gradle-troubleshooting",
    "sl-development-build-link-013"       to "https://doc.benrhine.com/engineering/development/build/build-logging",
    "sl-development-build-link-014"       to "https://doc.benrhine.com/engineering/development/build/build-maven-artifacts",
    "sl-development-build-link-015"       to "https://doc.benrhine.com/engineering/development/build/build-maven-troubleshooting",
    "sl-development-containers-link"       to "https://doc.benrhine.com/engineering/development/containers/",
    "sl-development-containers-link-001"       to "https://doc.benrhine.com/engineering/development/containers/docker/docker",
    "sl-development-containers-link-002"       to "https://doc.benrhine.com/engineering/development/containers/",
    "sl-development-data-link"       to "https://doc.benrhine.com/engineering/development/data/",
    "sl-development-data-link-001"       to "https://doc.benrhine.com/engineering/development/data/data",
    "sl-development-data-link-002"       to "https://doc.benrhine.com/engineering/development/data/data-cleanup",
    "sl-development-data-link-003"       to "https://doc.benrhine.com/engineering/development/data/data-company-wide-meta-data",
    "sl-development-data-link-004"       to "https://doc.benrhine.com/engineering/development/data/data-file-types",
    "sl-development-data-link-005"       to "https://doc.benrhine.com/engineering/development/data/generation",
    "sl-development-data-link-006"       to "https://doc.benrhine.com/engineering/development/data/makers",
    "sl-development-data-link-007"       to "https://doc.benrhine.com/engineering/development/data/type",
    "sl-development-java-link"       to "https://doc.benrhine.com/engineering/development/java/",
    "sl-development-java-link-001"       to "https://doc.benrhine.com/engineering/development/java/java",
    "sl-development-java-link-002"       to "https://doc.benrhine.com/engineering/development/java/java-build-library-with-support-for-multiple-versions-of-java",
    "sl-development-java-link-003"       to "https://doc.benrhine.com/engineering/development/java/java-gradle",
    "sl-development-java-link-004"       to "https://doc.benrhine.com/engineering/development/java/java-maven",
    "sl-development-lib-link"       to "https://doc.benrhine.com/engineering/development/libraries/libraries",
    "sl-development-log-link"       to "https://doc.benrhine.com/engineering/development/logging/",
    "sl-development-log-link-001"       to "https://doc.benrhine.com/engineering/development/logging/logging",
    "sl-development-log-link-002"       to "https://doc.benrhine.com/engineering/development/logging/logging-application-stage",
    "sl-development-log-link-003"       to "https://doc.benrhine.com/engineering/development/logging/logging-masking-sensitive-data",
    "sl-development-log-link-004"       to "https://doc.benrhine.com/engineering/development/logging/logging-when-logs-are-not-working",
    "sl-development-metrics-link"       to "https://doc.benrhine.com/engineering/development/metrics/metric-reference",
    "sl-development-recovery-link"       to "https://doc.benrhine.com/engineering/development/recovery/disaster-recovery",
    "sl-development-slack-link"       to "https://doc.benrhine.com/engineering/development/slack/slack",
    "sl-development-style-link"       to "https://doc.benrhine.com/engineering/development/style/style",
    "sl-development-testing-link"       to "https://doc.benrhine.com/engineering/development/testing/",
    "sl-development-testing-link-001"       to "https://doc.benrhine.com/engineering/development/testing/testing",
    "sl-development-testing-link-002"       to "https://doc.benrhine.com/engineering/development/testing/testing-api-smoke",
    "sl-development-testing-link-003"       to "https://doc.benrhine.com/engineering/development/testing/testing-integration",
    "sl-development-testing-link-004"       to "https://doc.benrhine.com/engineering/development/testing/testing-load",
    "sl-development-testing-link-005"       to "https://doc.benrhine.com/engineering/development/testing/testing-logging-codebuild",
    "sl-development-testing-link-006"       to "https://doc.benrhine.com/engineering/development/testing/testing-mocking",
    "sl-development-testing-link-007"       to "https://doc.benrhine.com/engineering/development/testing/testing-test-containers",
    "sl-development-testing-link-008"       to "https://doc.benrhine.com/engineering/development/testing/testing-thintegration",
    "sl-development-testing-link-009"       to "https://doc.benrhine.com/engineering/development/testing/testing-unit",
    "sl-development-3rdp-link"       to "https://doc.benrhine.com/engineering/development/third-party-services/third-party-services",
    "sl-documentation-link"     to "https://doc.benrhine.com/engineering/documentation",
    "sl-environments-link"      to "https://doc.benrhine.com/engineering/environments",
    "sl-glossary-link"          to "https://doc.benrhine.com/engineering/glossary",
    "sl-naming-conventions-link" to "https://doc.benrhine.com/engineering/naming-conventions",
    "sl-onboarding-link"        to "https://doc.benrhine.com/engineering/onboarding",
    "sl-ops-link"               to "https://doc.benrhine.com/engineering/ops",
    "sl-ops-link-001"           to "https://doc.benrhine.com/engineering/ops/argo-cd",
    "sl-ops-link-002"           to "https://doc.benrhine.com/engineering/ops/helm",
    "sl-ops-link-003"           to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code",
    "sl-ops-link-003-001"       to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/aws",
    "sl-ops-link-003-001-001"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/aws/aws-base-iam-roles",
    "sl-ops-link-003-001-002"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/aws/aws-cf",
    "sl-ops-link-003-001-003"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/aws/aws-cf-create-cloudwatch-insight-query",
    "sl-ops-link-003-001-004"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/aws/aws-cf-create-codebuild-job",
    "sl-ops-link-003-001-005"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/aws/aws-cf-create-codepipeline",
    "sl-ops-link-003-001-006"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/aws/aws-cf-create-dynamo-table",
    "sl-ops-link-003-001-007"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/aws/aws-cf-create-role-complete",
    "sl-ops-link-003-001-008"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/aws/aws-cf-create-system-property",
    "sl-ops-link-003-001-009"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/aws/aws-cf-get-system-property",
    "sl-ops-link-003-001-010"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/aws/aws-cf-troubleshooting",
    "sl-ops-link-003-002"       to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison",
    "sl-ops-link-003-002-001"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-codebuild",
    "sl-ops-link-003-002-002"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-codepipeline",
    "sl-ops-link-003-002-003"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-cross-account",
    "sl-ops-link-003-002-004"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-event-rule",
    "sl-ops-link-003-002-005"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-iac",
    "sl-ops-link-003-002-006"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-iac-role",
    "sl-ops-link-003-002-007"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-iac-role-policies",
    "sl-ops-link-003-002-008"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-iac-variables",
    "sl-ops-link-003-002-009"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-multiple-environments",
    "sl-ops-link-003-002-010"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-s3",
    "sl-ops-link-003-002-011"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-s3-policies",
    "sl-ops-link-003-002-012"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-ssm",
    "sl-ops-link-003-002-013"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/compare-state",
    "sl-ops-link-003-002-014"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/comparison/comparison-modules",
    "sl-ops-link-003-003"       to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/examples",
    "sl-ops-link-003-003-001"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/examples/aws-cf-sls-codecommit-approval-rules-template",
    "sl-ops-link-003-003-002"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/examples/aws-cf-sls-codecommit-notification-rules",
    "sl-ops-link-003-004"       to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless",
    "sl-ops-link-003-004-001"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless/sls-aws-create-cloudwatch-insight-query",
    "sl-ops-link-003-004-002"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless/sls-aws-create-codebuild-job",
    "sl-ops-link-003-004-003"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless/sls-aws-create-codepipeline",
    "sl-ops-link-003-004-004"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless/sls-aws-create-dynamo-table",
    "sl-ops-link-003-004-005"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless/sls-aws-create-role-complete",
    "sl-ops-link-003-004-006"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless/sls-aws-create-system-property",
    "sl-ops-link-003-004-007"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless/sls-aws-get-system-property",
    "sl-ops-link-003-004-008"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless/sls-profiles-properties-env-vars",
    "sl-ops-link-003-004-009"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless/sls-step-1-domain-deploy",
    "sl-ops-link-003-004-010"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless/sls-step-2-app-resource-deploy",
    "sl-ops-link-003-004-011"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless/sls-step-3-application-deploy",
    "sl-ops-link-003-004-012"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/serverless/sls-troubleshooting",
    "sl-ops-link-003-005"       to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform",
    "sl-ops-link-003-005-001"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-aws-create-cloudwatch-insight-query",
    "sl-ops-link-003-005-002"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-aws-create-codebuild-job",
    "sl-ops-link-003-005-003"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-aws-create-codepipeline",
    "sl-ops-link-003-005-004"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-aws-create-dynamo-table",
    "sl-ops-link-003-005-005"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-aws-create-role-complete",
    "sl-ops-link-003-005-006"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-aws-create-system-property",
    "sl-ops-link-003-005-007"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-aws-get-system-property",
    "sl-ops-link-003-005-008"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-chechov",
    "sl-ops-link-003-005-009"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-check-if-resource-exists",
    "sl-ops-link-003-005-010"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-compliance",
    "sl-ops-link-003-005-011"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-conditionals",
    "sl-ops-link-003-005-012"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-github",
    "sl-ops-link-003-005-013"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-lifecycle-hooks",
    "sl-ops-link-003-005-014"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-module",
    "sl-ops-link-003-005-015"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-reference",
    "sl-ops-link-003-005-016"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-state",
    "sl-ops-link-003-005-017"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-testing",
    "sl-ops-link-003-005-018"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-troubleshooting",
    "sl-ops-link-003-005-019"   to "https://doc.benrhine.com/engineering/ops/infrastructure-as-code/terraform/tf-variables",
    "sl-ops-link-004"           to "https://doc.benrhine.com/engineering/ops/infraweave",
    "sl-ops-link-005"           to "https://doc.benrhine.com/engineering/ops/kubernetes",
    "sl-projects-link"          to "https://doc.benrhine.com/engineering/projects",
    "sl-properties-link"        to "https://doc.benrhine.com/engineering/properties",
    "sl-support-link"           to "https://doc.benrhine.com/engineering/support",
    "sl-support-link-001"       to "https://doc.benrhine.com/engineering/support/run-books",
    "sl-support-link-002"       to "https://doc.benrhine.com/engineering/support/run-books/run-book-lyp-service",
    "sl-team-link"              to "https://doc.benrhine.com/engineering/team",
    "sl-team-link-001"          to "https://doc.benrhine.com/engineering/team/team-ba-ticket-breakdown",
    "sl-team-link-002"          to "https://doc.benrhine.com/engineering/team/team-project-planning",
    "sl-team-link-003"          to "https://doc.benrhine.com/engineering/team/team-scrum-flow-variations",
    "sl-team-link-004"          to "https://doc.benrhine.com/engineering/team/team-scrum-misc",
    "sl-team-link-005"          to "https://doc.benrhine.com/engineering/team/team-scrum-pointing-and-scale",
    "sl-team-link-006"          to "https://doc.benrhine.com/engineering/team/team-scrum-sprint-total-view",
    "sl-team-link-007"          to "https://doc.benrhine.com/engineering/team/ticket-template-story",
    "sl-team-link-008"          to "https://doc.benrhine.com/engineering/team/team-ticketing-flow",
    "sl-team-link-009"          to "https://doc.benrhine.com/engineering/team/team-adr",
    "sl-team-link-010"          to "https://doc.benrhine.com/engineering/team/team-adr-template",
    "sl-team-link-011"          to "https://doc.benrhine.com/engineering/team/team-best-practices",
    "sl-team-link-012"          to "https://doc.benrhine.com/engineering/team/team-doc-as-code",
    "sl-team-link-013"          to "https://doc.benrhine.com/engineering/team/team-meeting-notes",
    "sl-team-link-014"          to "https://doc.benrhine.com/engineering/team/team-naming-conventions",
    "sl-team-link-015"          to "https://doc.benrhine.com/engineering/team/team-ticket-template-spike",
    "sl-team-link-016"          to "https://doc.benrhine.com/engineering/team/team-ticket-template-story",
    "sl-team-link-017"          to "https://doc.benrhine.com/engineering/team/team-video-training",
    "sl-tooling-link"           to "https://doc.benrhine.com/engineering/tooling",
    "tl-tooling-link-01"        to "https://doc.benrhine.com/engineering/tooling/tooling-asciidoc",
    "tl-tooling-link-02"        to "https://doc.benrhine.com/engineering/tooling/tooling-certificates",
    "tl-tooling-link-03"        to "https://doc.benrhine.com/engineering/tooling/tooling-coverity",
    "tl-tooling-link-04"        to "https://doc.benrhine.com/engineering/tooling/tooling-git",
    "tl-tooling-link-06"        to "https://doc.benrhine.com/engineering/tooling/tooling-git-hooks",
    "tl-tooling-link-07"        to "https://doc.benrhine.com/engineering/tooling/tooling-git-main-vs-master",
    "tl-tooling-link-09"        to "https://doc.benrhine.com/engineering/tooling/tooling-git-trunk-based-development",
    "tl-tooling-link-10"        to "https://doc.benrhine.com/engineering/tooling/tooling-jira",
    "tl-tooling-link-11"        to "https://doc.benrhine.com/engineering/tooling/tooling-linting",
    "tl-tooling-link-12"        to "https://doc.benrhine.com/engineering/tooling/tooling-logging",
    "tl-tooling-link-13"        to "https://doc.benrhine.com/engineering/tooling/tooling-markdown",
    "tl-tooling-link-14"        to "https://doc.benrhine.com/engineering/tooling/tooling-properties-and-parameters",
    "tl-tooling-link-15"        to "https://doc.benrhine.com/engineering/tooling/tooling-sonar",
    "tl-tooling-link-16"        to "https://doc.benrhine.com/engineering/tooling/tooling-ssl",
    "tl-tooling-link-17"        to "https://doc.benrhine.com/engineering/tooling/tooling-windows",
    "tl-tooling-link-18"        to "https://doc.benrhine.com/engineering/tooling/tooling-java-faster-strings",
    "tl-tooling-link-19"        to "https://doc.benrhine.com/engineering/tooling/tooling-java-use-of-the-keyword-final",
    "tl-tooling-link-20"        to "https://doc.benrhine.com/engineering/tooling/tooling-argocd",
    "tl-tooling-link-21"        to "https://doc.benrhine.com/engineering/tooling/tooling-cloud",
    "tl-tooling-link-22"        to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws",
    "tl-tooling-link-022-001"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-alarms",
    "tl-tooling-link-022-002"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-cdk",
    "tl-tooling-link-022-003"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-cdk-troubleshooting",
    "tl-tooling-link-022-004"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-cli",
    "tl-tooling-link-022-005"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-cli-troubleshooting",
    "tl-tooling-link-022-006"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-cloudwatch-insights-json",
    "tl-tooling-link-022-007"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-codeartifact",
    "tl-tooling-link-022-008"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-codecommit",
    "tl-tooling-link-022-009"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-cognito",
    "tl-tooling-link-022-010"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-dynamodb",
    "tl-tooling-link-022-011"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-dynamodb-troubleshooting",
    "tl-tooling-link-022-012"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-glue",
    "tl-tooling-link-022-013"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-iam",
    "tl-tooling-link-022-014"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-lambda",
    "tl-tooling-link-022-015"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-lambda-authorizers",
    "tl-tooling-link-022-016"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-lambda-complete-json",
    "tl-tooling-link-022-017"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-lambda-layers",
    "tl-tooling-link-022-018"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-sam",
    "tl-tooling-link-022-019"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-sam-troubleshooting",
    "tl-tooling-link-022-020"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-setup-accounts",
    "tl-tooling-link-022-021"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-setup-code-build",
    "tl-tooling-link-022-022"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-setup-code-build-multiple",
    "tl-tooling-link-022-023"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-setup-code-build-single",
    "tl-tooling-link-022-024"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-setup-code-commit",
    "tl-tooling-link-022-025"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-setup-code-pipeline",
    "tl-tooling-link-022-026"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-sns",
    "tl-tooling-link-022-027"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-ssm",
    "tl-tooling-link-022-028"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-system-parameters",
    "tl-tooling-link-022-029"   to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-aws/tooling-aws-setup-cli",
    "tl-tooling-link-23"        to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-gcp",
    "tl-tooling-link-24"        to "https://doc.benrhine.com/engineering/tooling/tooling-cloud-azure",
    "tl-tooling-link-25"        to "https://doc.benrhine.com/engineering/tooling/tooling-helm",
    "tl-tooling-link-26"        to "https://doc.benrhine.com/engineering/tooling/tooling-infraweave",
    "tl-tooling-link-27"        to "https://doc.benrhine.com/engineering/tooling/tooling-kubernetes",
    "sl-training-link"          to "https://doc.benrhine.com/engineering/training",
    "sl-troubleshooting-link"   to "https://doc.benrhine.com/engineering/troubleshooting",
    "sl-reference-link"         to "https://doc.benrhine.com/links/reference",
    "sl-research-link"          to "https://doc.benrhine.com/links/research",
    "tl-how-to-link"            to "https://doc.benrhine.com/engineering/training/how-to",
    "tl-how-to-link-001"        to "https://doc.benrhine.com/engineering/training/how-to/automatically-get-custom-dependency-version",
    "tl-how-to-link-002"        to "https://doc.benrhine.com/engineering/training/how-to/aws-add-lambda-to-vpc",
    "tl-how-to-link-003"        to "https://doc.benrhine.com/engineering/training/how-to/aws-build-time-credentialing",
    "tl-how-to-link-004"        to "https://doc.benrhine.com/engineering/training/how-to/aws-codebuild-after-recreating-resources",
    "tl-how-to-link-005"        to "https://doc.benrhine.com/engineering/training/how-to/aws-configure-chatbot",
    "tl-how-to-link-006"        to "https://doc.benrhine.com/engineering/training/how-to/aws-configure-dlq",
    "tl-how-to-link-007"        to "https://doc.benrhine.com/engineering/training/how-to/aws-configure-dlq-alarm",
    "tl-how-to-link-008"        to "https://doc.benrhine.com/engineering/training/how-to/aws-configure-metric-filters",
    "tl-how-to-link-009"        to "https://doc.benrhine.com/engineering/training/how-to/aws-configure-metric-filters-alarm",
    "tl-how-to-link-010"        to "https://doc.benrhine.com/engineering/training/how-to/aws-configure-sns-or-sqs",
    "tl-how-to-link-011"        to "https://doc.benrhine.com/engineering/training/how-to/aws-create-a-custom-domain",
    "tl-how-to-link-012"        to "https://doc.benrhine.com/engineering/training/how-to/aws-cross-account-deploy",
    "tl-how-to-link-013"        to "https://doc.benrhine.com/engineering/training/how-to/aws-get-generated-lambda-uri",
    "tl-how-to-link-014"        to "https://doc.benrhine.com/engineering/training/how-to/aws-ip-whitelist-rest",
    "tl-how-to-link-015"        to "https://doc.benrhine.com/engineering/training/how-to/aws-lambda-layer-import-certificate",
    "tl-how-to-link-016"        to "https://doc.benrhine.com/engineering/training/how-to/aws-lambda-layers",
    "tl-how-to-link-017"        to "https://doc.benrhine.com/engineering/training/how-to/aws-powertools",
    "tl-how-to-link-018"        to "https://doc.benrhine.com/engineering/training/how-to/aws-programatically-assume-role",
    "tl-how-to-link-019"        to "https://doc.benrhine.com/engineering/training/how-to/aws-secrets-manager",
    "tl-how-to-link-020"        to "https://doc.benrhine.com/engineering/training/how-to/aws-send-sqs-postman",
    "tl-how-to-link-021"        to "https://doc.benrhine.com/engineering/training/how-to/aws-snap-start-java",
    "tl-how-to-link-022"        to "https://doc.benrhine.com/engineering/training/how-to/aws-sns-access-policy",
    "tl-how-to-link-023"        to "https://doc.benrhine.com/engineering/training/how-to/aws-verify-resource-location-from-running-lambda",
    "tl-how-to-link-024"        to "https://doc.benrhine.com/engineering/training/how-to/build-and-publish-library",
    "tl-how-to-link-025"        to "https://doc.benrhine.com/engineering/training/how-to/configure-artifact-release-job",
    "tl-how-to-link-026"        to "https://doc.benrhine.com/engineering/training/how-to/configure-maven-to-connect-to-aws-codeartifact",
    "tl-how-to-link-027"        to "https://doc.benrhine.com/engineering/training/how-to/connect-unit-test-execution-to-sonar-in-ci",
    "tl-how-to-link-028"        to "https://doc.benrhine.com/engineering/training/how-to/convert-serverless-to-cloudformation",
    "tl-how-to-link-029"        to "https://doc.benrhine.com/engineering/training/how-to/convert-serverless-to-sam",
    "tl-how-to-link-030"        to "https://doc.benrhine.com/engineering/training/how-to/create-base-cloudformation-template",
    "tl-how-to-link-031"        to "https://doc.benrhine.com/engineering/training/how-to/create-custom-slack-bot",
    "tl-how-to-link-032"        to "https://doc.benrhine.com/engineering/training/how-to/git-remove-tag",
    "tl-how-to-link-033"        to "https://doc.benrhine.com/engineering/training/how-to/local-deploy",
    "tl-how-to-link-034"        to "https://doc.benrhine.com/engineering/training/how-to/programmatically-check-dependency-update",
    "tl-how-to-link-035"        to "https://doc.benrhine.com/engineering/training/how-to/howto-setup-mac-os",
    "tl-how-to-link-036"        to "https://doc.benrhine.com/engineering/training/how-to/howto-setup-linux",
    "tl-how-to-link-037"        to "https://doc.benrhine.com/engineering/training/how-to/howto-setup-windows",
    "tl-how-to-link-038"        to "https://doc.benrhine.com/engineering/training/how-to/howto-access-requests",
    "tl-how-to-link-039"        to "https://doc.benrhine.com/engineering/training/how-to/howto-configure-aws-cli-quick-start",
    "tl-how-to-link-040"        to "https://doc.benrhine.com/engineering/training/how-to/howto-configure-bookmarks",
    "tl-how-to-link-041"        to "https://doc.benrhine.com/engineering/training/how-to/howto-configure-git-oidc-aws",
    "tl-how-to-link-042"        to "https://doc.benrhine.com/engineering/training/how-to/howto-configure-git-quick-start",
    "tl-how-to-link-043"        to "https://doc.benrhine.com/engineering/training/how-to/howto-configure-git-ssh",
    "tl-how-to-link-044"        to "https://doc.benrhine.com/engineering/training/how-to/howto-configure-maven-quick-start",
    "tl-how-to-link-045"        to "https://doc.benrhine.com/engineering/training/how-to/howto-request-slack-access",
    "tl-how-to-link-046"        to "https://doc.benrhine.com/engineering/training/how-to/howto-install-terraform",
    "tl-how-to-link-047"        to "https://doc.benrhine.com/engineering/training/how-to/howto-configure-git-pat",
    "tl-how-to-link-048"        to "https://doc.benrhine.com/engineering/training/how-to/howto-configure-github-workflow",
    "tl-how-to-link-049"        to "https://doc.benrhine.com/engineering/training/how-to/howto-setup-windows-wsl",
    "tl-how-to-link-050"        to "https://doc.benrhine.com/engineering/training/how-to/howto-git-remove-all-history",
    "tl-how-to-link-051"        to "https://doc.benrhine.com/engineering/training/how-to/howto-configure-git-actions",
    "tl-tutorials-link"         to "https://doc.benrhine.com/engineering/training/tutorials",
    "tl-tutorials-link-01"      to "https://doc.benrhine.com/engineering/training/tutorials/tutorial-cloudwatch-send-slack",


)
// # end::confluencePublisher3[]


group = "com.benrhine"
//version = "$extra.year.$extra.month.$extra.week"

if (gitBuildNumber == null) {
    version = "$year.$month.$week"
} else {
    version = "$year.$month.$week.$gitBuildNumber"
}


//System.out.println("i am a test $version\n")

repositories {
    mavenCentral()
}

// Configuration to hold extension JARs
val asciidoctorExt by configurations.creating

dependencies {
    // Tabbed code extension
    asciidoctorExt("com.bmuschko:asciidoctorj-tabbed-code-extension:0.3")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// Attempt and getting asciidoc Tabs to work - Left for future reference
//tasks.withType<AsciidoctorTask>().configureEach {
//    // Defer adding the extension and copying assets until execution
//    doFirst {
//        // Add extension JARs to Asciidoctor runtime
//        configurations(asciidoctorExt.name)
//        // Copy tabbed-code CSS/JS to output dir before rendering
//        asciidoctorExt.forEach { file ->
//            copy {
//                from(zipTree(file)) {
////                    include("META-INF/resources/tabbed-code-extension.css")
//                    include("META-INF/resources/tabbed-code-extension.js")
//                }
//                into(outputDir)
//            }
//
//            // Copy your custom CSS from project source
//            copy {
//                from("documentation/css/tabbed-code-custom.css")
//                into(outputDir)
//            }
//        }
//    }
//
//    attributes(
//        mapOf(
//            "source-highlighter" to "highlightjs",
//            "highlightjs-theme" to "github",
//            // Link the copied resources in the HTML
//            "stylesheet" to "tabbed-code-custom.css",
//            "scripts" to "tabbed-code-extension.js"
//        )
//    )
//
//    baseDirFollowsSourceDir()
//}

// https://asciidoctor.github.io/asciidoctor-gradle-plugin/master/user-guide/
// https://www.google.com/search?q=gradle+configure+asciidoctor&oq=gradle+configure+asciidoctor&gs_lcrp=EgZjaHJvbWUyBggAEEUYOTIHCAEQIRigATIHCAIQIRigATIHCAMQIRifBTIHCAQQIRifBTIHCAUQIRifBTIHCAYQIRifBTIHCAcQIRifBTIHCAgQIRifBTIHCAkQIRifBdIBCDY1NjRqMGo3qAIAsAIA&sourceid=chrome&ie=UTF-8
// https://github.com/asciidoctor/asciidoctor-gradle-plugin/issues/458
tasks {
    //==================================================================================================================
    // Build all AsciiDoc:
    //==================================================================================================================
    // # tag::docConfluence[]
    register<AsciidoctorTask>("docGeneral")  {
        setSourceDir(file(projectDir))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs"))
        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn("docBusiness", "docArchitecture", "docArchive", "docLinks", "docEngineering")
    }
    // # end::docConfluence[]
    // # tag::docConfluenceBusiness[]
    register<AsciidoctorTask>("docBusiness")  {
        setSourceDir(file("business"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/business"))
        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn()
    }
    register<AsciidoctorTask>("docArchitecture")  {
        setSourceDir(file("architecture"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/architecture"))
        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn()
    }
    register<AsciidoctorTask>("docArchive")  {
        setSourceDir(file("archive"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/archive"))
        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn()
    }
    register<AsciidoctorTask>("docLinks")  {
        setSourceDir(file("links"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/links"))
        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn()
    }
    register<AsciidoctorTask>("docEngineering")  {
        setSourceDir(file("engineering"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering"))
        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn(
            "docEngTraining", "docEngTooling", "docEngTeam", "docEngSupport", "docEngProperties",
            "docEngEnvironments", "docEngOps", "docEngDeployment", "docEngDev"
        )
//        dependsOn(
//            "docConfluenceEngAdr", "docConfluenceEngBuild", "docConfluenceEngCloud",
//            "docConfluenceEngDeployment", "docConfluenceEngGit", "docConfluenceEngHowTo", "docConfluenceEngJava",
//            "docConfluenceEngMisc", "docConfluenceEngReference", "docConfluenceEngSetups", "docConfluenceEngSupport",
//            "docConfluenceEngTeam", "docConfluenceEngTechSpecs", "docConfluenceEngTest",
//            "docConfluenceEngTooling", "docConfluenceEngTutorial")
    }
    // # end::docConfluenceBusiness[]

    // START - Build all sub-pages under eng
    register<AsciidoctorTask>("docEngDeployment")  {
        setSourceDir(file("engineering/deployment"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/deployment"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngTraining")  {
        setSourceDir(file("engineering/training"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/training"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn("docEngTrainingTutorial", "docEngTrainingHowTo")
    }
    register<AsciidoctorTask>("docEngTrainingTutorial")  {
        setSourceDir(file("engineering/training/tutorials"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/training/tutorials"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngTrainingHowTo")  {
        setSourceDir(file("engineering/training/how-to"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/training/how-to"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngTooling")  {
        setSourceDir(file("engineering/tooling"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/tooling"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn(
            "docEngToolingCloudAws"
        )
    }
    register<AsciidoctorTask>("docEngToolingCloudAws")  {
        setSourceDir(file("engineering/tooling/tooling-cloud-aws"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/tooling/tooling-cloud-aws"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngTeam")  {
        setSourceDir(file("engineering/team"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/team"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngSupport")  {
        setSourceDir(file("engineering/support"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/support"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn("docEngSupportRunBooks")
    }
    register<AsciidoctorTask>("docEngSupportRunBooks")  {
        setSourceDir(file("engineering/support/run-books"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/support/run-books"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngProperties")  {
        setSourceDir(file("engineering/properties"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/properties"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngEnvironments")  {
        setSourceDir(file("engineering/environments"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/environments"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngOps")  {
        setSourceDir(file("engineering/ops"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/ops"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn(
            "docEngOpsKubernetes", "docEngOpsKubernetes", "docEngOpsHelm", "docEngOpsInfraweave",
            "docEngOpsArgoCD", "docEngOpsIaC"
        )
    }
    register<AsciidoctorTask>("docEngOpsKubernetes")  {
        setSourceDir(file("engineering/ops/kubernetes"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/ops/kubernetes"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngOpsHelm")  {
        setSourceDir(file("engineering/ops/helm"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/ops/helm"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngOpsInfraweave")  {
        setSourceDir(file("engineering/ops/infraweave"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/ops/infraweave"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngOpsArgoCD")  {
        setSourceDir(file("engineering/ops/argo-cd"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/ops/argo-cd"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngOpsIaC")  {
        setSourceDir(file("engineering/ops/infrastructure-as-code"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/ops/infrastructure-as-code"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn(
            "docEngOpsIaCAws", "docEngOpsIaCComparison", "docEngOpsIaCExamples", "docEngOpsIaCServerless",
            "docEngOpsIaCTerraform"
        )
    }
    register<AsciidoctorTask>("docEngOpsIaCAws")  {
        setSourceDir(file("engineering/ops/infrastructure-as-code/aws"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/ops/infrastructure-as-code/aws"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngOpsIaCComparison")  {
        setSourceDir(file("engineering/ops/infrastructure-as-code/comparison"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/ops/infrastructure-as-code/comparison"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngOpsIaCExamples")  {
        setSourceDir(file("engineering/ops/infrastructure-as-code/examples"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/ops/infrastructure-as-code/examples"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngOpsIaCServerless")  {
        setSourceDir(file("engineering/ops/infrastructure-as-code/serverless"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/ops/infrastructure-as-code/serverless"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngOpsIaCTerraform")  {
        setSourceDir(file("engineering/ops/infrastructure-as-code/terraform"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/ops/infrastructure-as-code/terraform"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngDev")  {
        setSourceDir(file("engineering/development"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn(
            "docEngDevBuild", "docEngDevContainers", "docEngDevData", "docEngDevJava",
            "docEngDevLibraries", "docEngDevLogging", "docEngDevMetrics", "docEngDevRecovery",
            "docEngDevSlack", "docEngDevStyle", "docEngDevTesting", "docEngDevThirdPartyServices"
        )
    }
    register<AsciidoctorTask>("docEngDevBuild")  {
        setSourceDir(file("engineering/development/build"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/build"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngDevContainers")  {
        setSourceDir(file("engineering/development/containers"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/containers"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
        // Fill in will all sub-page jobs
        dependsOn(
            "docEngDevContainersDocker"
        )
    }
    register<AsciidoctorTask>("docEngDevContainersDocker")  {
        setSourceDir(file("engineering/development/containers/docker"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/containers/docker"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngDevData")  {
        setSourceDir(file("engineering/development/data"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/data"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngDevJava")  {
        setSourceDir(file("engineering/development/java"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/java"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngDevLibraries")  {
        setSourceDir(file("engineering/development/libraries"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/libraries"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngDevLogging")  {
        setSourceDir(file("engineering/development/logging"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/logging"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngDevMetrics")  {
        setSourceDir(file("engineering/development/metrics"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/metrics"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngDevRecovery")  {
        setSourceDir(file("engineering/development/recovery"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/recovery"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngDevSlack")  {
        setSourceDir(file("engineering/development/slack"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/slack"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngDevStyle") {
        setSourceDir(file("engineering/development/style"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/style"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngDevTesting")  {
        setSourceDir(file("engineering/development/testing"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/testing"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    register<AsciidoctorTask>("docEngDevThirdPartyServices") {
        setSourceDir(file("engineering/development/third-party-services"))
        sources(delegateClosureOf<PatternSet> {
            include("*.adoc")
        })
        setOutputDir(file("build/docs/engineering/development/third-party-services"))

        // Define custom attributes using a map
        attributes(asciiDocAttributes )
    }
    //---
//    register<AsciidoctorTask>("docConfluenceEngAdr")  {
//        setSourceDir(file("documentation/engineering/eng-adr"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-adr"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//    register<AsciidoctorTask>("docConfluenceEngDeployment")  {
//        setSourceDir(file("documentation/engineering/eng-deployment"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-deployment"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//
//    register<AsciidoctorTask>("docConfluenceEngMisc")  {
//        setSourceDir(file("documentation/engineering/eng-misc"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-misc"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//    register<AsciidoctorTask>("docConfluenceEngReference")  {
//        setSourceDir(file("documentation/engineering/eng-reference"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-reference"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//    register<AsciidoctorTask>("docConfluenceEngSetups")  {
//        setSourceDir(file("documentation/engineering/eng-setups"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-setups"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//    register<AsciidoctorTask>("docConfluenceEngSupport")  {
//        setSourceDir(file("documentation/engineering/eng-support"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-support"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//    register<AsciidoctorTask>("docConfluenceEngTeam")  {
//        setSourceDir(file("documentation/engineering/eng-team"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-team"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//    register<AsciidoctorTask>("docConfluenceEngTechSpecs")  {
//        setSourceDir(file("documentation/engineering/eng-tech-specs"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-tech-specs"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//    register<AsciidoctorTask>("docConfluenceEngTest")  {
//        setSourceDir(file("documentation/engineering/eng-test"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/dpd-test"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//    register<AsciidoctorTask>("docConfluenceEngTooling")  {
//        setSourceDir(file("documentation/engineering/eng-tooling"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-tooling"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//        // Fill in will all sub-page jobs
//        dependsOn("docConfluenceEngGit", "docConfluenceEngJava")
//    }
//
//    // END - Build all sub-pages under eng
//
//    // START - Build all sub-pages under eng-tooling
//    register<AsciidoctorTask>("docConfluenceEngBuild")  {
//        setSourceDir(file("documentation/engineering/eng-tooling/eng-build"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-tooling/eng-build"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//    register<AsciidoctorTask>("docConfluenceEngCloud")  {
//        setSourceDir(file("documentation/engineering/eng-tooling/eng-cloud"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-tooling/eng-cloud"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//    register<AsciidoctorTask>("docConfluenceEngGit")  {
//        setSourceDir(file("documentation/engineering/eng-tooling/eng-git"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-tooling/eng-git"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//    register<AsciidoctorTask>("docConfluenceEngJava")  {
//        setSourceDir(file("documentation/engineering/eng-tooling/eng-java"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-tooling/eng-java"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//    // END - Build all sub-pages under eng-tooling
//
//    register<AsciidoctorTask>("docThisProject")  {
//        setSourceDir(projectDir)
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-project"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }
//
//    register<AsciidoctorTask>("docTerraformAwsGitHubOidcSetup")  {
//        setSourceDir(file("account/aws-github-oidc-setup"))
//        sources(delegateClosureOf<PatternSet> {
//            include("*.adoc")
//        })
//        setOutputDir(file("build/docs/engineering/eng-project/aws-github-oidc-setup"))
//
//        // Define custom attributes using a map
//        attributes(asciiDocAttributes )
//    }

    //==================================================================================================================
    // Publish to Confluence:
    //==================================================================================================================
    // Publish Top Level Repository Documentation
    // # tag::confluencePublisher5[]
//    register<PublishToConfluenceTask>("publishDpdBaseCloudInfrastructure") {
//        asciiDocRootFolder      = projectDir
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectsPageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//    }
//    // # end::confluencePublisher5[]
//    // Publish Architectural Decision Records for the project/repository
//    register<PublishToConfluenceTask>("publishDpdBaseCloudInfrastructureAdr") {
//        asciiDocRootFolder      = file("documentation/digital-product-delivery/adr")
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/adr"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1PageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdBaseCloudInfrastructure")
//    }
//    register<PublishToConfluenceTask>("publishDpdBaseCloudInfrastructureAdrDecisions") {
//        asciiDocRootFolder      = file("documentation/digital-product-delivery/adr/decisions")
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/adr/decisions"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1AdrPageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdBaseCloudInfrastructure")
//    }
//    // Publish Tech Specs for the project/repository
//    register<PublishToConfluenceTask>("publishDpdBaseCloudInfrastructureTechSpec") {
//        asciiDocRootFolder      = file("documentation/digital-product-delivery/tech-spec")
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/tech-spec"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1PageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdBaseCloudInfrastructure")
//    }
//    register<PublishToConfluenceTask>("publishDpdBaseCloudInfrastructureTechSpecDocumentation") {
//        asciiDocRootFolder      = file("documentation/digital-product-delivery/tech-spec/specs")
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/tech-spec/specs"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1TechSpecPageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdBaseCloudInfrastructureTechSpec")
//    }
//    // Publish Tech Specs for the project/repository
//    register<PublishToConfluenceTask>("publishDpdBaseCloudInfrastructureChangelog") {
//        asciiDocRootFolder      = file("documentation/digital-product-delivery/changelog")
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/changelog"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1PageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdBaseCloudInfrastructure")
//    }
//    register<PublishToConfluenceTask>("publishDpdBaseCloudInfrastructureChangelogDocumentation") {
//        asciiDocRootFolder      = file("documentation/digital-product-delivery/changelog/logs")
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/changelog/logs"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1ChangelogPageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdBaseCloudInfrastructureChangelog")
//    }
//    // Publish aws-github-oidc-setup Terraform/OpenTofu documentation
//    // # tag::confluencePublisher6[]
//    register<PublishToConfluenceTask>("publishAwsGitHubOidcSetupTf") {
//        asciiDocRootFolder      = file("infrastructure/aws/account-dpd-dev-aws-dp/dev/aws-github-oidc-setup")
//        // this line must be unique
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/aws-github-oidc-setup"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1PageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdBaseCloudInfrastructure")
//    }
//    // # end::confluencePublisher6[]
//    // # tag::confluencePublisher7[]
//    register<PublishToConfluenceTask>("publishAwsGitHubOidcSetupDocumentationTf") {
//        asciiDocRootFolder      = file("infrastructure/aws/account-dpd-dev-aws-dp/dev/aws-github-oidc-setup/documentation")
//        // this line must be unique
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/aws-github-oidc-setup/documentation"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1Sub1PageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishAwsGitHubOidcSetupTf")
//    }
//    // # end::confluencePublisher7[]
//    // Publish dpd-base-cloud-resources Terraform/OpenTofu documentation
//    register<PublishToConfluenceTask>("publishDpdBaseCloudInfrastructureTf") {
//        asciiDocRootFolder      = file("infrastructure/aws/account-dpd-dev-aws-dp/dev/dpd-base-cloud-infrastructure")
//        // this line must be unique
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/dpd-base-cloud-infrastructure"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1PageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdBaseCloudInfrastructure")
//    }
//    register<PublishToConfluenceTask>("publishDpdBaseCloudInfrastructureDocumentationTf") {
//        asciiDocRootFolder      = file("infrastructure/aws/account-dpd-dev-aws-dp/dev/dpd-base-cloud-infrastructure/documentation")
//        // this line must be unique
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/dpd-base-cloud-infrastructure/documentation"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1Sub2PageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdBaseCloudInfrastructureTf")
//    }
//    // Publish dpd-base-log-queries Terraform/OpenTofu documentation
//    register<PublishToConfluenceTask>("publishDpdBaseLogQueriesTf") {
//        asciiDocRootFolder      = file("infrastructure/aws/account-dpd-dev-aws-dp/dev/dpd-base-log-queries")
//        // this line must be unique
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/dpd-base-log-queries"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1PageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdBaseCloudInfrastructure")
//    }
//    register<PublishToConfluenceTask>("publishDpdBaseLogQueriesDocumentationTf") {
//        asciiDocRootFolder      = file("infrastructure/aws/account-dpd-dev-aws-dp/dev/dpd-base-log-queries/documentation")
//        // this line must be unique
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/dpd-base-log-queries/documentation"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1Sub3PageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdBaseLogQueriesTf")
//    }
//    // Publish dpd-ci-role-create Terraform/OpenTofu documentation
//    register<PublishToConfluenceTask>("publishDpdCiRoleCreateTf") {
//        asciiDocRootFolder      = file("infrastructure/aws/account-dpd-dev-aws-dp/dev/dpd-ci-role-create")
//        // this line must be unique
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/dpd-ci-role-create"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1PageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdBaseCloudInfrastructure")
//    }
//    register<PublishToConfluenceTask>("publishDpdCiRoleCreateDocumentationTf") {
//        asciiDocRootFolder      = file("infrastructure/aws/account-dpd-dev-aws-dp/dev/dpd-ci-role-create/documentation")
//        // this line must be unique
//        outputDir               = "${rootProject.layout.buildDirectory}/docs/project/dpd-ci-role-create/documentation"
//        rootConfluenceUrl       = confluenceUrl
//        spaceKey                = confluenceSpace
//        ancestorId              = confluenceDpdTeamProjectRepo1Sub4PageId
//        username                = confluenceUser
//        password                = confluenceAuthorization
//        notifyWatchers          = false
//        // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//        // Set to ensure if someone creates a page on confluence rather than here it is not lost
//        orphanRemovalStrategy   = OrphanRemovalStrategy.KEEP_ORPHANS
//        restApiVersion          = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//        versionMessage          = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//        setProperty("attributes", asciiDocAttributes)
//
//        dependsOn("publishDpdCiRoleCreateTf")
//    }

    // WILL ONLY DO LAST COMMIT UNLESS GITHUB ACTION CHECKOUT FETCH DEPTH VALUE IS MODIFIED
    // https://www.google.com/search?q=git-changelog-gradle-plugin+locally+gets+all+commits+in+ci+only+gets+most+recent+commit&sca_esv=a95b1a567a7f3cbf&ei=f1DlaIwf25vQ8Q-Y0o-RCQ&ved=0ahUKEwjM2pf80JKQAxXbDTQIHRjpI5IQ4dUDCBA&uact=5&oq=git-changelog-gradle-plugin+locally+gets+all+commits+in+ci+only+gets+most+recent+commit&gs_lp=Egxnd3Mtd2l6LXNlcnAiV2dpdC1jaGFuZ2Vsb2ctZ3JhZGxlLXBsdWdpbiBsb2NhbGx5IGdldHMgYWxsIGNvbW1pdHMgaW4gY2kgb25seSBnZXRzIG1vc3QgcmVjZW50IGNvbW1pdEjlZ1DpJFjBZnADeAGQAQKYAZgCoAGWOKoBBzI3LjMwLjO4AQPIAQD4AQGYAiKgAsIkwgIKEAAYsAMY1gQYR8ICBBAAGB7CAggQABiABBiiBMICBRAAGO8FwgIIEAAYogQYiQXCAgUQIRigAcICBRAhGKsCwgIHECEYoAEYCpgDAIgGAZAGB5IHBjQuMjguMqAH9MkBsgcGMS4yOC4yuAewJMIHBzAuMjQuMTDIB1o&sclient=gws-wiz-serp
    register<GitChangelogTask>("generateGitChangelog") {
        file.set(file("documentation/digital-product-delivery/changelog/logs/changelog.adoc"))
        templateContent.set("""
= Build - $version | Published  - $today

include::../../../_includes/third-level-includes.adoc[]

    {{#tags}}
    ## {{name}}
     {{#issues}}
      {{#hasIssue}}
       {{#hasLink}}
    ### {{name}} [{{issue}}]({{link}}) {{title}} {{#hasIssueType}} *{{issueType}}* {{/hasIssueType}} {{#hasLabels}} {{#labels}} *{{.}}* {{/labels}} {{/hasLabels}}
       {{/hasLink}}
       {{^hasLink}}
    ### {{name}} {{issue}} {{title}} {{#hasIssueType}} *{{issueType}}* {{/hasIssueType}} {{#hasLabels}} {{#labels}} *{{.}}* {{/labels}} {{/hasLabels}}
       {{/hasLink}}
      {{/hasIssue}}
      {{^hasIssue}}
    ### {{name}}
      {{/hasIssue}}
    
      {{#commits}}
== **{{messageTitle}}**

    
    {{#messageBodyItems}}
     * {{.}}
    {{/messageBodyItems}}
    
https://github.com/{{ownerName}}/{{repoName}}/commit/{{hash}}[{{hash}}] - {{authorName}} *{{commitTime}}*

      {{/commits}}
     {{/issues}}
    {{/tags}}
        """);
    }

    register<GitChangelogTask>("generateGitReleaseChangelog") {
        file.set(file("documentation/digital-product-delivery/changelog/logs/release-changelog.adoc"))
        templateContent.set("""
= Release - $version | Published  - $today

include::../../../_includes/third-level-includes.adoc[]

    {{#tags}}
    ## {{name}}
     {{#issues}}
      {{#hasIssue}}
       {{#hasLink}}
    ### {{name}} [{{issue}}]({{link}}) {{title}} {{#hasIssueType}} *{{issueType}}* {{/hasIssueType}} {{#hasLabels}} {{#labels}} *{{.}}* {{/labels}} {{/hasLabels}}
       {{/hasLink}}
       {{^hasLink}}
    ### {{name}} {{issue}} {{title}} {{#hasIssueType}} *{{issueType}}* {{/hasIssueType}} {{#hasLabels}} {{#labels}} *{{.}}* {{/labels}} {{/hasLabels}}
       {{/hasLink}}
      {{/hasIssue}}
      {{^hasIssue}}
    ### {{name}}
      {{/hasIssue}}
    
      {{#commits}}
== **{{messageTitle}}**

    
    {{#messageBodyItems}}
     * {{.}}
    {{/messageBodyItems}}
    
https://github.com/{{ownerName}}/{{repoName}}/commit/{{hash}}[{{hash}}] - {{authorName}} *{{commitTime}}*

      {{/commits}}
     {{/issues}}
    {{/tags}}
        """);
    }

}



// Make primary publish task depend on custom tasks to ensure everything is correctly published
// # tag::confluencePublisher8[]
tasks.named("publishToConfluence") {
    dependsOn("publishDpdBaseCloudInfrastructure", "publishDpdBaseCloudInfrastructureAdr", "publishDpdBaseCloudInfrastructureTechSpec",
        "publishDpdBaseCloudInfrastructureChangelog", "publishAwsGitHubOidcSetupTf", "publishDpdBaseCloudInfrastructureTf",
        "publishDpdBaseLogQueriesTf", "publishDpdCiRoleCreateTf", "publishDpdBaseCloudInfrastructureAdrDecisions",
        "publishAwsGitHubOidcSetupDocumentationTf", "publishDpdBaseCloudInfrastructureDocumentationTf", "publishDpdBaseLogQueriesDocumentationTf",
        "publishDpdCiRoleCreateDocumentationTf", "publishDpdBaseCloudInfrastructureChangelogDocumentation")
}
// # end::confluencePublisher8[]
// =====================================================================================================================
// Publish Documentation to Confluence Configuration | PRIMARY EXECUTION TASK
// =====================================================================================================================
// =====================================================================================================================
// WARNING!!! This DOES NOT use the output of ANY of the asciidoc tasks. It completely regenerates the documentation in a
// Confluence specific xhtml during execution.
// =====================================================================================================================
// Many of these values should go into secrets manager
// # tag::confluencePublisher1[]
//confluencePublisher {
//    asciiDocRootFolder          = file("documentation/digital-product-delivery")
//    outputDir                   = "${rootProject.layout.buildDirectory}/docs"
//    rootConfluenceUrl           = confluenceUrl
//    spaceKey                    = confluenceSpace
//    // this is the top level space pageId for Global Data Product -> Integrated Data Services -> Digital Product Delivery
//    ancestorId                  = confluenceDpdTeamPageId
//    username                    = confluenceUser
//    // # end::confluencePublisher1[]
//    password                    = confluenceAuthorization
//    // # tag::confluencePublisher2[]
//    notifyWatchers              = false
//    // THIS MUST ABSOLUTELY BE SET TO KEEP_ORPHANS OR IT WILL DELETE EVERYTHING IN THE SPACE - DO NOT CHANGE!!!
//    // Set to ensure if someone creates a page on confluence rather than here it is not lost
//    orphanRemovalStrategy       = OrphanRemovalStrategy.KEEP_ORPHANS
//    restApiVersion              = confluenceApi                              // MUST be v1, v2 is primarily for cloud instances
//    versionMessage              = confluenceVersionMessage                   // Set confluence version message to match git commit
//
//    setProperty("attributes", asciiDocAttributes)
//}
// # end::confluencePublisher2[]

/** --------------------------------------------------------------------------------------------------------------------
 * Helper Functions:
 * ------------------------------------------------------------------------------------------------------------------ */

fun getDate(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")

    return LocalDate.now().toString() //format(formatter)
}

fun getCurrentYear(): Int {
    val currentDate = LocalDate.now()

    return currentDate.year
}

fun getCurrentMonth(): Int {
    val currentDate = LocalDate.now()

    return currentDate.monthValue
}

fun getCurrentJavaVersion(javaHome: String): String {
    if (javaHome.contains("11")) {
        return "Java 11"
    } else if (javaHome.contains("17")) {
        return "Java 17"
    } else if (javaHome.contains("21")) {
        return "Java 21"
    } else if (javaHome.contains("24")) {
        return "Java 24"
    } else {
        return "UNKNOWN"
    }
}
