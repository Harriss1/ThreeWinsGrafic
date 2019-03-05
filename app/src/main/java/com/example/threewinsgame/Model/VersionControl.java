package com.example.threewinsgame.Model;
//package com.example.smallcalculations.BuildConfig;

import com.example.threewinsgame.BuildConfig;


public class VersionControl {

    //edit manually:
    private final int major = 1;
    private final int minor = 0;
    private final int patch = 0;
    private final boolean isSnapshot = false;
    private final String comment = "firstVersioningTry";
    private final boolean useComment = false;

    //inserted automatically
    BuildConfig buildConfig = new BuildConfig();
    private final int formattedDate = buildConfig.formattedDate;
    private final int formattedTime = buildConfig.formattedTime;


    public VersionControl() {

    }

    public int getVersionCode() {
        return 10000;
        //its recommended to include the API and the target resolution type, I don't do that because
        //I want to learn basic versioning that is generally applied to all languages.
        //Just getting doubts, because every language and API maybe needs individual versioning...
    }

    public String getVersionString() {
        String suffix = "";
        if (isSnapshot) suffix = "pre-";
        String comment = "";
        if (useComment) comment="-"+this.comment;
        String version =
                Integer.toString(major) + "." +
                Integer.toString(minor) + "." +
                Integer.toString(patch) + "-" +
                suffix +
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

            buildConfigField "int", "FOO", "52"
        }
        debug{
            buildConfigField "int", "FOO", "52"

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
