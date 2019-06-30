package nanodegree.dfw.perm.bakingapp.data.handler.baking;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Steps {

        private Integer id;
        private String shortDescription;
        private String description;
        private String videoURL;
        private String thumbnailURL;

        /**
         * No args constructor for use in serialization
         */
        public Steps() {
        }

        /**
         * @param id
         * @param shortDescription
         * @param description
         * @param videoURL
         * @param thumbnailURL
         */
        public Steps(Integer id, String shortDescription, String description, String videoURL, String thumbnailURL) {
            super();
            this.id = id;
            this.shortDescription = shortDescription;
            this.description = description;
            this.videoURL = videoURL;
            this.thumbnailURL = thumbnailURL;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVideoURL() {
            return videoURL;
        }

        public void setVideoURL(String videoURL) {
            this.videoURL = videoURL;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }

        public void setThumbnailURL(String thumbnailURL) {
            this.thumbnailURL = thumbnailURL;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("id", id).append("shortDescription", shortDescription).append("description", description).append("videoURL", videoURL).append("thumbnailURL", thumbnailURL).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(id).append(shortDescription).append(description).append(videoURL).append(thumbnailURL).toHashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Steps) == false) {
                return false;
            }
            Steps rhs = ((Steps) other);
            return new EqualsBuilder().append(id, rhs.id).append(shortDescription, rhs.shortDescription).append(description, rhs.description).append(videoURL, rhs.videoURL).append(thumbnailURL, rhs.thumbnailURL).isEquals();
        }

    }
