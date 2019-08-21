package nanodegree.dfw.perm.bakingapp.data.handler.baking;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Steps implements Parcelable {

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
         * @param id    Id for the Individual Step
         * @param shortDescription summary for the step
         * @param description      details
         * @param videoURL         supposed full video resource location
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


//        public void setId(Integer id) {
//            this.id = id;
//        }

    protected Steps(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

    public Integer getId() {
    return id;
}
        public String getShortDescription() {
            return shortDescription;
        }
        public String getDescription() {
        return description;
    }
        public String getVideoURL() {
        return videoURL;
    }
        public String getThumbnailURL() {
        return thumbnailURL;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}
