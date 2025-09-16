import React from "react";

interface ProfileProps {
  name: string;
  imageSrc: string;
}

const Profile: React.FC<ProfileProps> = ({ name, imageSrc }) => {
  return (
    <div className="flex flex-col items-center">
      <img src={imageSrc} className="w-20 h-20 rounded-full" />
      <h2 className="text-base font-semibold text-black">{name}</h2>
    </div>
  );
};

export default Profile;
