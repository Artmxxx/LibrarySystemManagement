/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#FFA500', // Orange color for borders and accents
        background: '#FFFFFF', // White background color
      },
    },
  },
  plugins: [require('daisyui')],
  daisyui: {
    themes: [
      {
        mytheme: {
          primary: '#FFA500',
          secondary: '#FFFFFF',
          accent: '#FFA500',
          neutral: '#333333',
          "base-100": "#FFFFFF",
        },
      },
    ],
  },
};


