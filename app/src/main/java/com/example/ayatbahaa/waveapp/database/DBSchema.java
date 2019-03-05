package com.example.ayatbahaa.waveapp.database;

public final class DBSchema {
    public static final class FilesStorage{
        public static final String PERSONAL_IMAGES= "personalImages";
    }
    public static abstract class Firestore{
        public static final class OrphansCol{
            public static final String NAME= "Orphans";

            public static final class Fields{
                public static final String STRING_NAME= "name";
                public static final String STRING_PLACE_OF_BIRTH= "placeOfBirth";
                public static final String STRING_DESCRIPTION= "description";
                public static final String STRING_EDUCATION= "education";
                public static final String STRING_GENDER= "gender";
                public static final String STRING_BIRTHDAY= "birthday";
                public static final String STRING_PICTURE_URL = "pictureUrl";

                // array of strings = AOS
                public static final String AOS_DISEASES= "diseases";
                public static final String AOS_SKILLS_= "skills";
                public static final String AOS_HOBBIES = "hobbies";

                public static final String BOOLEAN_MARRIED = "married";
                public static final String BOOLEAN_STUDYING_CURRENTLY = "studyingCurrently";


                public static final String NUMBER_EDUCATION_LEVEL= "educationLevel";
            }
        }
        public static final class ElderlyCol{
            public static final String NAME= "Elderly";

            public static final class Fields{
                public static final String STRING_NAME= "name";
                public static final String STRING_PLACE_OF_BIRTH= "placeOfBirth";
                public static final String STRING_DESCRIPTION= "description";
                public static final String STRING_EDUCATION= "education";
                public static final String STRING_GENDER= "gender";
                public static final String STRING_BIRTHDAY= "birthday";
                public static final String STRING_PICTURE_URL = "pictureUrl";

                // array of strings = AOS
                public static final String AOS_DISEASES= "diseases";
                public static final String AOS_SKILLS_= "skills";
                public static final String AOS_HOBBIES = "hobbies";

                public static final String BOOLEAN_MARRIED = "married";
                public static final String BOOLEAN_STUDYING_CURRENTLY = "studyingCurrently";


                public static final String NUMBER_EDUCATION_LEVEL= "educationLevel";
            }
        }
    }
}
