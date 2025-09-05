import arrowDown from "../../assets/arrowDown.svg";
import arrowUp from "../../assets/arrowUp.svg";

interface ScrollButtonProps {
    isUp: boolean;
    className?: string;
    onClick?: () => void;
}

export default function ScrollButton({ isUp, className, onClick }: ScrollButtonProps) {
    return (
        <button
            onClick={onClick}
            className={`flex items-center justify-center rounded-full border-2 border-white p-3 bg-white opacity-75 hover:opacity-50 transition-opacity duration-200 ${className}`}
            aria-label={isUp ? "Scroll up" : "Scroll down"}
        >
            <img src={isUp ? arrowUp : arrowDown} alt={isUp ? "Arrow up" : "Arrow down"} />
        </button>
    );
}
