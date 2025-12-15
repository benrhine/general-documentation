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
    "sl-development-link"       to "https://doc.benrhine.com/engineering/development",
    "sl-documentation-link"     to "https://doc.benrhine.com/engineering/documentation",
    "sl-environments-link"      to "https://doc.benrhine.com/engineering/environments",
    "sl-glossary-link"          to "https://doc.benrhine.com/engineering/glossary",
    "sl-naming-conventions-link" to "https://doc.benrhine.com/engineering/naming-conventions",
    "sl-onboarding-link"        to "https://doc.benrhine.com/engineering/onboarding",
    "sl-ops-link"               to "https://doc.benrhine.com/engineering/ops",
    "sl-projects-link"          to "https://doc.benrhine.com/engineering/projects",
    "sl-properties-link"        to "https://doc.benrhine.com/engineering/properties",
    "sl-support-link"           to "https://doc.benrhine.com/engineering/support",
    "sl-team-link"              to "https://doc.benrhine.com/engineering/team",
    "sl-team-link-001"          to "https://doc.benrhine.com/engineering/team/ba-ticket-breakdown",
    "sl-team-link-002"          to "https://doc.benrhine.com/engineering/team/project-planning",
    "sl-team-link-003"          to "https://doc.benrhine.com/engineering/team/scrum-flow-variations",
    "sl-team-link-004"          to "https://doc.benrhine.com/engineering/team/scrum-misc",
    "sl-team-link-005"          to "https://doc.benrhine.com/engineering/team/scrum-pointing-and-scale",
    "sl-team-link-006"          to "https://doc.benrhine.com/engineering/team/scrum-sprint-total-view",
    "sl-team-link-007"          to "https://doc.benrhine.com/engineering/team/ticket-template-story",
    "sl-team-link-008"          to "https://doc.benrhine.com/engineering/team/ticketing-flow",
    "sl-tooling-link"           to "https://doc.benrhine.com/engineering/tooling",
    "tl-tooling-link-01"        to "https://doc.benrhine.com/engineering/tooling/tooling-asciidoc",
    "tl-tooling-link-02"        to "https://doc.benrhine.com/engineering/tooling/tooling-certificates",
    "tl-tooling-link-03"        to "https://doc.benrhine.com/engineering/tooling/tooling-coverity",
    "tl-tooling-link-04"        to "https://doc.benrhine.com/engineering/tooling/tooling-git",
    "tl-tooling-link-05"        to "https://doc.benrhine.com/engineering/tooling/tooling-git-actions",
    "tl-tooling-link-06"        to "https://doc.benrhine.com/engineering/tooling/tooling-git-hooks",
    "tl-tooling-link-07"        to "https://doc.benrhine.com/engineering/tooling/tooling-git-main-vs-master",
    "tl-tooling-link-08"        to "https://doc.benrhine.com/engineering/tooling/tooling-git-remove-all-history",
    "tl-tooling-link-09"        to "https://doc.benrhine.com/engineering/tooling/tooling-git-trunk-based-development",
    "tl-tooling-link-10"        to "https://doc.benrhine.com/engineering/tooling/tooling-jira",
    "tl-tooling-link-11"        to "https://doc.benrhine.com/engineering/tooling/tooling-linting",
    "tl-tooling-link-12"        to "https://doc.benrhine.com/engineering/tooling/tooling-logging",
    "tl-tooling-link-13"        to "https://doc.benrhine.com/engineering/tooling/tooling-markdown",
    "tl-tooling-link-14"        to "https://doc.benrhine.com/engineering/tooling/tooling-properties-and-parameters",
    "tl-tooling-link-15"        to "https://doc.benrhine.com/engineering/tooling/tooling-sonar",
    "tl-tooling-link-16"        to "https://doc.benrhine.com/engineering/tooling/tooling-ssl",
    "tl-tooling-link-17"        to "https://doc.benrhine.com/engineering/tooling/tooling-windows",
    "sl-training-link"          to "https://doc.benrhine.com/engineering/training",
    "sl-troubleshooting-link"   to "https://doc.benrhine.com/engineering/troubleshooting",
    "sl-reference-link"         to "https://doc.benrhine.com/links/reference",
    "sl-research-link"          to "https://doc.benrhine.com/links/research",
    "tl-how-to-link"            to "https://doc.benrhine.com/engineering/training/how-to",
    "tl-how-to-link-001"        to "https://doc.benrhine.com/engineering/training/how-to/automattically-get-custom-dependency-version",
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
    "tl-how-to-link-013"        to "https://doc.benrhine.com/engineering/training/how-to/aws-get-generated-lambda-url",
    "tl-how-to-link-014"        to "https://doc.benrhine.com/engineering/training/how-to/aws-ip-whitelist-rest",
    "tl-how-to-link-015"        to "https://doc.benrhine.com/engineering/training/how-to/aws-lambda-layer-import-certificate",
    "tl-how-to-link-016"        to "https://doc.benrhine.com/engineering/training/how-to/aws-lambda-layer",
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
            "docEngEnvironments"
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
