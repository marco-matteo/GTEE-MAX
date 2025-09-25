import React from "react";

interface ProfileProps {
    name: string;
    caption: string;
    imageSrc: string;
}

const Profile: React.FC<ProfileProps> = ({name, caption, imageSrc}) => {
    return (
        <div className="absolute bottom-4 left-4">
            <div className="flex flex-col items-center min-w-20 w-min">
                {
                    name ? (

                        <img src={imageSrc} className="w-20 h-20 rounded-full"/>
                    ) : ""
                }
                <h2 className="text-base font-semibold text-white">{name}</h2>
            </div>
            <p className="text-white">{caption}</p>
        </div>

    );
};

export default Profile;
