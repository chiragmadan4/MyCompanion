package com.example.chiragmadan.mycompanion;

public class Feedback {

        private String roll_no;
        private String feedback_content;
        private String time;

        public Feedback(String roll_no, String feedback_content, String time) {
            this.roll_no = roll_no;
            this.feedback_content = feedback_content;
            this.time = time;
        }

        public String getRoll_no() {
            return roll_no;
        }

        public void setRoll_no(String roll_no) {
            this.roll_no = roll_no;
        }

        public String getFeedback_content() {
            return feedback_content;
        }

        public void setFeedback_content(String feedback_content) {
            this.feedback_content = feedback_content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }


