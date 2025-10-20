import React from "react";
import {useNavigate} from "react-router";

interface ProfileProps {
    name: string;
    caption: string;
    imageSrc: string;
}

const Profile: React.FC<ProfileProps> = ({name, caption, imageSrc}) => {
    const navigate = useNavigate()
    return (
        <div className="absolute bottom-4 left-4">
            <div className="flex flex-col items-center min-w-20 w-min">
                {
                    name ? (
                        <img src={imageSrc} className="w-20 h-20 rounded-full"/>
                    ) : ""
                }
                <button onClick={() => {
                    navigate(`/profile/${name}`)
                }}>
                    <h2 className="text-base font-semibold text-white hover:font-bold">{name}</h2>
                </button>
            </div>
            <p className="text-white">{caption}</p>
        </div>

    );
};

export default Profile;
