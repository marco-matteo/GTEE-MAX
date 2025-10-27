export default function NotFound() {
    return (
        <main className="grid min-h-screen place-items-center bg-black px-6 py-24 sm:py-32 lg:px-8">
            <div className="text-center">
                <h1 className="text-5xl font-semibold text-indigo-400">404</h1>
                <h1 className="mt-4 text-5xl font-semibold tracking-tight text-balance text-white sm:text-7xl">Page not found</h1>
                <p className="mt-6 text-lg font-medium text-pretty text-gray-400 sm:text-xl/8">Sorry, we couldn’t find the page you’re looking for.</p>
                <div className="mt-10 flex items-center justify-center gap-x-6">
                    <a href="/" className="text-white rounded-md bg-indigo-500 px-8 py-4 hover:bg-indigo-600 transition-colors duration-150">Home</a>
                </div>
            </div>
        </main>

    )
}