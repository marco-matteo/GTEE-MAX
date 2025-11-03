import {describe, it, expect, vi, beforeEach, type Mocked} from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import ProfileScreen from './ProfileScreen.tsx'
import * as reactRouter from 'react-router'
import axios from 'axios'

vi.mock('axios')
const mockedAxios = axios as Mocked<typeof axios>

describe('ProfileScreen', () => {
    const useParamsMock = vi.spyOn(reactRouter, 'useParams')
    const useNavigateMock = vi.spyOn(reactRouter, 'useNavigate')

    beforeEach(() => {
        useParamsMock.mockReturnValue({ username: 'testuser' })
        useNavigateMock.mockReturnValue(vi.fn())
    })

    it('renders loading initially', () => {
        render(<ProfileScreen />)
        expect(document.body.innerHTML).toContain('h-screen w-full bg-black')
    })

    it('fetches and displays user data and videos', async () => {
        mockedAxios.get.mockImplementation((url) => {
            if (url.includes('/auth/user/check')) return Promise.resolve({ data: {} })
            if (url.includes('/user/')) return Promise.resolve({ data: { username: 'testuser', views: 10, favorite: null } })
            if (url.includes('/video/user/')) return Promise.resolve({ data: [{ id: 1, title: 'Video 1' }] })
            if (url.includes('/video/stream/')) return Promise.resolve({ data: new Blob(['video content'], { type: 'video/mp4' }) })
            return Promise.resolve({ data: {} })
        })

        render(<ProfileScreen />)

        await waitFor(() => expect(screen.getByText('@testuser')).toBeDefined())
        expect(screen.getByText('views: 10')).toBeDefined()
        expect(document.querySelector('video')).toBeDefined()
    })

    it('navigates to 404 on fetch error', async () => {
        const navigateMock = vi.fn()
        useNavigateMock.mockReturnValue(navigateMock)

        mockedAxios.get.mockRejectedValue(new Error('Not Found'))

        render(<ProfileScreen />)

        await waitFor(() => expect(navigateMock).toHaveBeenCalledWith('/404'))
    })
})
