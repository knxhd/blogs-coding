import asyncio
import websockets
import websockets_routes

router = websockets_routes.Router()


@router.route("/AIGC/CAD_sub0.raw")
async def inference(websocket, path):
    print(websocket)
    try:
        async for message in websocket:
            print(message)
            websocket.send("xxxx")
    except websockets.exceptions.ConnectionClosed as e:
        print(f"Connection closed: {e}")


async def serve_forever(host, port):
    # rooter是一个装饰器，它的__call__函数有三个参数，第一个参数是self。
    # 所以这里我们需要使用lambda进行一个转换操作，因为serv的wshander函数只能接收2个参数
    async with websockets.serve(lambda x, y: router(x, y), host, port, max_size=2 ** 25):
        # async with websockets.serve(lambda x, y: router(x, y), host, port):
        await asyncio.Future()  # run forever


if __name__ == "__main__":
    asyncio.run(serve_forever(host="0.0.0.0", port=8080))
