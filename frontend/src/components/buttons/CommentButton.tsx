import { useState } from "react";
import commentIcon from "../../assets/comment.png";

interface CommentButtonProps {
  className?: string;
}

export default function CommentButton({ className }: CommentButtonProps) {
  const [comments, setComments] = useState(0);

  const handleComment = () => {
    setComments(comments + 1);
  };

  return (
    <div className={`flex flex-col items-center ${className}`}>
      <button
        onClick={handleComment}
        className="rounded-full p-2 hover:scale-110 transition"
      >
        <img src={commentIcon} alt="Comment" className="h-10 w-10" />
      </button>
      <span className="mt-2 text-black font-semibold">{comments}</span>
    </div>
  );
}
