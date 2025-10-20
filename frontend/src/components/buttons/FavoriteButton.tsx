import favorite from "../../assets/favorite.svg";

interface FavoriteButtonProps {
    className?: string;
    onClick?: () => void;
    active: boolean;
}

export default function FavoriteButton({ className, onClick, active }: FavoriteButtonProps) {
    return (
        <button
            className={"flex items-center justify-center rounded-full border-2 opacity-75 hover:opacity-50 transition-opacity duration-200"
             + `${ active ? "border-lime-200 bg-lime-200" : "border-white bg-white"} ${className}`}
            aria-label={active ? "unfavorite video" : "favorite video"}
            onClick={onClick}
        >
            <img src={favorite} alt={active ? "unfavorite" : "favorite"} className="object-cover" />
        </button>
    );
}
