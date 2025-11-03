import React, {useRef, useState} from "react";
import axios from "axios";

export default function RegisterBox({onRegisterSuccess, onCancelClick}: {
    onRegisterSuccess: () => void,
    onCancelClick: () => void
}) {
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const usernameInputRef = useRef<HTMLInputElement>(null)

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!usernameInputRef.current) return

        try {
            await axios.get(`http://localhost:8080/auth/user/check/${username}`);
            usernameInputRef.current.setCustomValidity("Username already exists");
            usernameInputRef.current.reportValidity();
            return;
        } catch {
            usernameInputRef.current.setCustomValidity("");
        }

        try {
            await axios.post(
                "http://localhost:8080/user/register",
                {username, password},
                {withCredentials: true}
            );
            onRegisterSuccess();
        } catch (error) {
            alert("Register failed");
            console.log(error);
        }
    };

    return (
        <div className="h-screen w-full bg-black flex flex-col justify-center">
            <form
                className="w-1/3 h-1/2 mx-auto flex flex-col justify-center gap-5 bg-white px-24 py-8 rounded-2xl"
                onSubmit={handleRegister}
            >
                <h1 className="text-3xl mx-auto font-bold">Register</h1>
                <input
                    ref={usernameInputRef}
                    type="text"
                    placeholder="Username"
                    className="border p-5 text-lg rounded-xl"
                    maxLength={20}
                    value={username}
                    onChange={(e) => {
                        setUsername(e.target.value)
                        usernameInputRef.current?.setCustomValidity("");
                    }
                    }
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    className="border p-5 text-lg rounded-xl"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button
                    className="py-2 rounded-xl bg-purple-500 text-xl font-bold text-white"
                    type="submit"
                >
                    Register
                </button>
                <button
                    className="py-2 rounded-xl bg-gray-300 text-xl font-semibold"
                    type="button"
                    onClick={onCancelClick}
                >
                    Cancel
                </button>
            </form>
        </div>
    )
}
