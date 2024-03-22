import { cookies } from 'next/headers';
import { NextResponse, type NextRequest } from 'next/server'
import { getSession } from '../scripts/serverConnection';
 
export async function middleware(request: NextRequest) {
    
    const response = NextResponse.next();

    var path = request.nextUrl.pathname;
    
    if (!path.startsWith("/request") && !path.startsWith("/cp")) {
        return;
    }

    if (!cookies().has("token")) {
        return Response.redirect(new URL("/", request.url));
    }

    var token = cookies().get("token")?.value;
    var json:any = await getSession(token);

    if (json === null || json.uuid === null) return Response.redirect(new URL("/", request.url));
    response.cookies.set({
        name: "uuid",
        value: json.uuid,
        httpOnly: true,
        path: "/"
    })

    return response;
}

export const config = {
    matcher: "/:path*"
}