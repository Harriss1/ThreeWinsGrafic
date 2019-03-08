package com.example.threewinsgame.Model;
//package com.example.smallcalculations.BuildConfig;

import com.example.threewinsgame.BuildConfig;
//todo decide if version numbers are set in gradle or here
//todo make grade output the same string as filename

public class VersionControl {

    //edit manually:
    private final int major = 0;
    private final int minor = 1;
    private final int patch = 0;
    private final boolean labelSnapshot = false;
    private String snapSuffix = "pre-"; //add minus after suffix e.g. "beta-"
    private String comment = "-firstVersioningTry"; //add minus before comment e.g. "-addedLanguageOption"
    private final boolean useComment = false;

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

        String version =
                Integer.toString(major) + "." +
                Integer.toString(minor) + "." +
                Integer.toString(patch) + "-" +
                snapSuffix +
                Integer.toString(formattedDate) + "." +
                Integer.toString(formattedTime) +
                comment;

        //return "1.0.0-snap-20190304.2038-withSpice";
        return version;
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
