import React from "react";
import axios from "axios";

type UploadBoxProps = {
    onClose: () => void;
};

export default function VideoUploadScreen({onClose}: UploadBoxProps) {
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const formData = new FormData(e.currentTarget);
        try {
            const userRes = await axios.get("http://localhost:8080/auth/user", {
                withCredentials: true,
            })
            const username = userRes.data

            formData.append("creatorId", username)

            await axios.post("http://localhost:8080/video", formData, {
                withCredentials: true,
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            })
            onClose();
        } catch (error) {
            console.error("Upload Error", error)
            alert("Something went wrong while uploading")
        }
    };

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-10">
            <div className="bg-white rounded-xl p-6 w-1/3 max-w-2xl shadow-lg">
                <h2 className="text-2xl mb-4">Upload Video</h2>
                <form onSubmit={handleSubmit} className="flex flex-col gap-4">
                    <input
                        id="videoFile"
                        name="videoFile"
                        type="file"
                        accept="video/mp4"
                        className="border p-5 rounded"

                        required={true}
                    />
                    <input id="caption" name="caption" type="text" placeholder="Caption" required={true} maxLength={30}
                           className="border p-5 rounded text-lg font-bold"/>
                    <div className="flex justify-end gap-2">
                        <button
                            type="button"
                            onClick={onClose}
                            className="px-4 py-2 bg-gray-300 rounded font-bold hover:bg-gray-400 transition-colors duration-200"
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            className="px-4 py-2 bg-purple-500 text-white font-bold rounded hover:bg-purple-600 transition-colors duration-200"
                        >
                            Upload
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}
