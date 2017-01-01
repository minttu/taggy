import React from 'react'

export default function App({ children }) {
    return (
        <div>
            <nav>
                <h1>taggy</h1>
            </nav>
            <div className="content">
                {children}
            </div>
        </div>
    )
}