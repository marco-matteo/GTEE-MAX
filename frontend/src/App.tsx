import ScrollButton from "./components/buttons/ScrollButton.tsx";
import Profile from "./components/Profile/Profile.tsx";
import emptyProfile from "./assets/empty_profile.png";

export default function App() {
  return (
    <div className="min-h-screen w-full flex justify-center items-center bg-black opacity-75">
      <div className="flex flex-col gap-4 items-center">
        <div className="relative h-screen aspect-[9/16] bg-white rounded-xl shadow-lg flex flex-col justify-center items-center">
          <div className="absolute bottom-4 left-4">
            <Profile name="Peter Meier" imageSrc={emptyProfile} />
          </div>
          <ScrollButton
            isUp={true}
            className="h-16 w-16 absolute -right-20 mb-20"
          />
          <ScrollButton
            isUp={false}
            className="h-16 w-16 absolute -right-20 mt-20"
          />
        </div>
      </div>
    </div>
  );
}
