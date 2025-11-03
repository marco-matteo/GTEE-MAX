import { render, screen, waitFor, fireEvent } from "@testing-library/react"
import { describe, it, expect, vi, beforeEach } from "vitest"
import { MemoryRouter, Route, Routes } from "react-router"
import axios, {type AxiosResponse } from "axios"
import VideoScreen from "./VideoScreen"

vi.mock("axios")

vi.mock("./buttons/ScrollButton.tsx", () => ({
    default: ({ onClick, isUp }: { onClick: () => void; isUp: boolean }) => (
        <button onClick={onClick}>{isUp ? "Scroll Up" : "Scroll Down"}</button>
    ),
}))

vi.mock("./buttons/FavoriteButton.tsx", () => ({
    default: ({ active, onClick }: { active: boolean; onClick: () => void }) => (
        <button onClick={onClick}>{active ? "Unfavorite" : "Favorite"}</button>
    ),
}))

vi.mock("./Profile/Profile.tsx", () => ({
    default: ({ name, caption }: { name: string; caption: string }) => (
        <div>
            <p>{name}</p>
            <p>{caption}</p>
        </div>
    ),
}))

beforeEach(() => {
    vi.clearAllMocks()
})

interface MockVideo {
    id: number
    creatorId: string
    caption: string
}

describe("VideoScreen component", () => {
    it("renders empty state when no videoId param is provided", () => {
        render(
            <MemoryRouter initialEntries={["/video"]}>
                <Routes>
                    <Route path="/video" element={<VideoScreen />} />
                </Routes>
            </MemoryRouter>
        )

        expect(screen.getByText(/start scrolling/i)).toBeInTheDocument()
    })

    it("fetches and displays a video when a videoId param is present", async () => {
        const mockVideo: MockVideo = { id: 123, creatorId: "user1", caption: "cool vid" }

        vi.mocked(axios.get).mockResolvedValueOnce({ data: mockVideo } as AxiosResponse<MockVideo>)
        vi.mocked(axios.get).mockResolvedValueOnce({ data: false } as AxiosResponse<boolean>)

        render(
            <MemoryRouter initialEntries={["/video/123"]}>
                <Routes>
                    <Route path="/video/:videoId" element={<VideoScreen />} />
                </Routes>
            </MemoryRouter>
        )

        await waitFor(() => {
            expect(screen.getByText("user1")).toBeInTheDocument()
            expect(screen.getByText("cool vid")).toBeInTheDocument()
        })

        expect(axios.get).toHaveBeenCalledWith(
            "http://localhost:8080/video/123",
            expect.any(Object)
        )
    })

    it("toggles favorite state when favorite button is clicked", async () => {
        const mockVideo: MockVideo = { id: 10, creatorId: "user2", caption: "funny clip" }

        vi.mocked(axios.get).mockResolvedValueOnce({ data: mockVideo } as AxiosResponse<MockVideo>)
        vi.mocked(axios.get).mockResolvedValueOnce({ data: false } as AxiosResponse<boolean>)
        vi.mocked(axios.post).mockResolvedValue({} as AxiosResponse)
        vi.mocked(axios.delete).mockResolvedValue({} as AxiosResponse)

        render(
            <MemoryRouter initialEntries={["/video/10"]}>
                <Routes>
                    <Route path="/video/:videoId" element={<VideoScreen />} />
                </Routes>
            </MemoryRouter>
        )

        await screen.findByText("funny clip")

        const favButton = screen.getByText("Favorite")
        fireEvent.click(favButton)

        await waitFor(() => {
            expect(axios.post).toHaveBeenCalledWith(
                "http://localhost:8080/video/favorite/10",
                null,
                expect.any(Object)
            )
        })
    })

    it("calls history.back() when Scroll Up is clicked", () => {
        const mockBack = vi.spyOn(window.history, "back").mockImplementation(() => {})

        render(
            <MemoryRouter>
                <VideoScreen />
            </MemoryRouter>
        )

        const upButton = screen.getByText("Scroll Up")
        fireEvent.click(upButton)

        expect(mockBack).toHaveBeenCalled()
    })
})
