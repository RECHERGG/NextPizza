import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "../globals.css";
import SideBar from "../components/SideBar";
import { Providers } from "../providers";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "NextPizza",
  description: "For daily lunch",
};

export default function CPLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {

  return (
    <div className="flex h-screen w-full bg-gray-50 dark:bg-gray-900">
      <SideBar />
      <div className="flex flex-col w-full h-full mr-12">
        <Providers>
          {children}
        </Providers>
      </div>
    </div>
  );
}
