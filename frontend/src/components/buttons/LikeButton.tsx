import { useState } from "react";
import likeIcon from "../../assets/like.png";

interface LikeButtonProps {
  className?: string;
}

export default function LikeButton({ className }: LikeButtonProps) {
  const [likes, setLikes] = useState(0);

  const handleLike = () => {
    setLikes(likes + 1);
  };

  return (
    <div className={`flex flex-col items-center ${className}`}>
      <button
        onClick={handleLike}
        className="rounded-full p-2 hover:scale-110 transition"
      >
        <img src={likeIcon} alt="Like" className="h-10 w-10" />
      </button>
      <span className="mt-2 text-black font-semibold">{likes}</span>
    </div>
  );
}
