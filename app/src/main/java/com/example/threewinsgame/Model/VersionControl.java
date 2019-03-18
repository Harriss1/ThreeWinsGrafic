package com.example.threewinsgame.Model;

import com.example.threewinsgame.BuildConfig;
//todo decide if version numbers are set in gradle or here
//todo make gradle output the same string as filename

//todo suffix for labelling dev-versions?//
// patches are for inside changes and bug fixes that are meant to be released
// .dev01 are for new versions that are not meant to be released? no that doesn't work, because
// sometimes dev versions would make minor or even major changes.
//todo but how do I keep the public from recognising more than one version change?
// I don't want them to know "hey I made 10 versions of the UI until I released the next patch
// and went from 1.04.02-stable over 1.05.00-dev to 1.15.04-stable ... (stable=release)
// If I have no API there is no need to document versions, and I can only document with date stamps?
// I don't want my users to think about new versions when they use my app. Except for where changes
// matter and requires the user to learn new stuff! That's the case in games. But you always can
// for example use Youtube without knowing any changes to it. And the web API fields are documented
// separately.

//Todo alright here is the solution:
// do it as in Rimworld: alpha versions leading with 0.
// public string before internal version string: 0.17.1345 - the patch string stays hidden
// public versions are NEVER incrementing the patch code part
// if the internal version was 0.17.1345 and a patch was 0.17.134602 then the next public version
// MUST be labelled 0.17.1347 NOT 0.17.1346 no matter what (!)
// have a different string for the mod-api or only change it with incrementing releases
//

public class VersionControl {

    //edit manually:
    private final int major = 1;
    private final int minor = 0;
    private final int patch = 0;
    private final boolean labelSnapshot = true;
    private String snapSuffix = "alpha-"; //add minus after suffix e.g. "beta-"
    private String comment = "-firstVersioningTry"; //add minus before comment e.g. "-addedLanguageOption"
    private final boolean useComment = false;
    private final int pubMajor=0; //stays zero as long as its not finished or early access version
    private final int pubMinor=1; //this is the bit that increases if there are a lot of visible changes
    private final boolean isRelease=false; //adds a -dev suffix and prints timestamp
    //todo: get dev from gradle? (no, not needed)

    //inserted automatically
    BuildConfig buildConfig = new BuildConfig();
    private final int formattedDate = buildConfig.formattedDate;
    private final int formattedTime = buildConfig.formattedTime;
    /*todo minSdkVersion 16
    todo get Screen Sizes or GL Texture Format*/
    private final int minSdkVersion = 16;
    private final int screenSize = 0;


    public VersionControl() {
    }

    public int getVersionCode() {
        int versionCode =
                minSdkVersion * 10000000 +
                screenSize * 100000 +
                major * 10000 +
                minor * 100 +
                patch;
        return versionCode;
        //its suggested from a external android dev to include the API and the target resolution type
        // every language and API maybe needs individual versioning...
        //
        // For example, when the application version name is 3.1.0, the version
        // code for a minimum API level 4 APK would be something like 040030100.
        // The first two digits are reserved for the minimum API Level (4 in this case),
        // the third digit is for either screen sizes or GL texture formats (not used in this
        // example, so a 0 is assigned), and the last six digits are for the application’s version
        // name (3.1.0).
        // https://medium.com/@maxirosson/versioning-android-apps-d6ec171cfd82
    }

    public String getVersionString() {
        if (!labelSnapshot) snapSuffix = "";
        if (!useComment) comment= "";
        String publicVersionIndicator = "";
        if (!isRelease)
        {
            publicVersionIndicator +=
                    Integer.toString(pubMajor)+ "."+
                    Integer.toString(pubMinor)+ ".";
            snapSuffix="-dev";
        } else  snapSuffix ="";

        String versionOld =
                Integer.toString(major) + "." +
                        Integer.toString(minor) + "." +
                        Integer.toString(patch) + "-" +
                        snapSuffix +
                        Integer.toString(formattedDate) + "." +
                        Integer.toString(formattedTime) +
                        comment;

        String version =
                        publicVersionIndicator + "." +
                        Integer.toString(major) + "." +
                        Integer.toString(minor) + "." +
                        Integer.toString(patch) + "-" +
                                        snapSuffix +
                        Integer.toString(formattedDate) + "." +
                        Integer.toString(formattedTime) +
                                        comment;

        //return "1.0.0-snap-20190304.2038-withSpice";
        return version;

        //todo compare booth versioning strings and decide which one is better for general apps/games.

        // if dev: return "0.1.10101-dev-20190318.1923-addedModAPI";
        // if release: return "0.1.101"

        //if you release a version directly without alpha state don't add in the leading number
        //It's not necessary to write 1.23.10132 to say its a released version... (?)
        //no actually I always need the leading 1 or 0 to indicate finished or early access versions.
        //If I don't use early access I don't need the leading 1 or 0.

    }
}

/*
major: API change
minor: visible change, backwards compatible to given API
patch: inside changes, not visible

suffix: pre-release, debug, snapshot, development, release, release-candidate

I want to include date and time. Additionally a little comment string, less than 10 characters.

The API needs to be documented for this system to work.

Additionally github makes a versioning system less important for beginners.

Actually Gradle seems to be best for this purpose.

Still, I should use my own system, and learn to store that little incrementing number myself...


gradle script:
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
        debug{
            //def dateX = new Date().format("dd-MM-yyyy-hh-mm-ss")
            //buildConfigField "String", "buildDateTry", dateX
            def date = new Date()
            def formattedDate = date.format('yyyyMMdd')
            def formattedTime = date.format('HHmm')

            buildConfigField "int", "formattedDate", formattedDate
            buildConfigField "int", "formattedTime", formattedTime
        }
    }

    //1
    task addCurrentDate() {
        // 2
        android.applicationVariants.all { variant ->
            // 3
            variant.outputs.all { output ->
                // 4
                def date = new Date().format("dd-MM-yyyy")
                // 5
                def fileName = variant.name + "_" + date + ".apk"
                // 6
                output.outputFileName = fileName
            }
        }
    }
 */
