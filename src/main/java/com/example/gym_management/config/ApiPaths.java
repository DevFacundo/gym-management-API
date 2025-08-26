package com.example.gym_management.config;

    public class ApiPaths {

        private ApiPaths() {}

        public static final String URI_BASE_V1 = "/api/v1";
        public static final String MEMBER_BASE = URI_BASE_V1 + "/members";
        public static final String MEMBERSHIP_PLAN_BASE = URI_BASE_V1 + "/membership-plans";
        public static final String CLASS_SCHEDULE_BASE = URI_BASE_V1 + "/class-schedules";
        public static final String HEALTH_RECORD_BASE = URI_BASE_V1 + "/health-records";
        public static final String PATHOLOGY_BASE = URI_BASE_V1 + "/pathologies";
        public static final String PAYMENT_BASE = URI_BASE_V1 + "/payments";
}
