package com.beazle.pursuitvolley.FirebaseFirestoreTags;

public final class FirestoreTags {

    // placeholder tags, required since collections require at least one document in them
    // might not need this
    // public static String documentPlaceholderTAG = "placeholder_document";

    // players
    public static String playerCollection = "players";
    public static String playerDocumentFullname = "full_name";
    public static String playerDocumentAge = "age";
    public static String playerDocumentLocation = "location";
    public static String playerDocumentEmail = "email";
    public static String playerDocumentPhoneNumber = "phone_number";
    public static String playerDocumentTokens = "tokens";

    // coaches
    public static String coachCollection = "coaches";
    public static String coachAppointmentsCollection = "appointments";
    public static String coachDocumentFullname = "full_name";
    public static String coachDocumentAge = "age";
    public static String coachDocumentBio = "bio";
    public static String coachDocumentLocation = "location";

    // admin codes
    public static String adminCodesCollection = "admin_codes";
    public static String coachAccountDeletionCode = "coach_account_deletion_code";
    public static String coachAccountActivationCode = "coach_activation_code";
    public static String codeKey = "code";

    // upcoming events
    public static String upcomingEventsCollection = "upcoming_events";
    public static String upcomingEventTitle = "event_title";
    public static String upcomingEventDate = "event_date";
    public static String upcomingEventBeginTime = "event_begin_time";
    public static String upcomingEventEndTime = "event_end_time";
    public static String upcomingEventLocation = "event_location";

    // current appointments
    public static String playerCurrentAppointmentsCollection = "current_appointments";
    public static String currentAppointmentsCoachName = "coach_name";
    public static String playerCurrentAppointmentsDate = "date";
    public static String playerCurrentAppointmentsBeginTime = "begin_time";
    public static String playerCurrentAppointmentsEndTime = "end_time";
    public static String playerCurrentAppointmentsLocation = "location";
}
