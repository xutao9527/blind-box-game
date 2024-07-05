import {bbgConf} from "@/config/index.js";

class WebSocketSingleton {
    constructor(url) {
        if (WebSocketSingleton.instances[url]) {
            return WebSocketSingleton.instances[url];
        }

        this.url = url;
        this.socket = null;
        this.isConnected = false;
        // this.messages = [];
        this.error = null;

        WebSocketSingleton.instances[url] = this;
        this.connect();
    }

    static getInstance(url) {
        if (!WebSocketSingleton.instances) {
            WebSocketSingleton.instances = {};
        }
        if (!WebSocketSingleton.instances[url]) {
            WebSocketSingleton.instances[url] = new WebSocketSingleton(url);
        }
        return WebSocketSingleton.instances[url];
    }

    connect() {
        if (this.socket && this.isConnected) {
            return;
        }

        this.socket = new WebSocket(this.url);

        this.socket.onopen = () => {
            this.isConnected = true;
            console.log('WebSocket 已连接');
        };

        this.socket.onmessage = (event) => {
            // console.log('收到消息:', event.data)
        };

        this.socket.onerror = (err) => {
            this.error = err;
            console.error('WebSocket 错误:', err);
        };

        this.socket.onclose = () => {
            this.isConnected = false;
            console.log('WebSocket 已断开');
        };
    }

    sendMessage(message) {
        if (this.socket && this.isConnected) {
            this.socket.send(message);
        } else {
            console.error('WebSocket 未连接');
        }
    }

    closeConnection() {
        if (this.socket) {
            this.socket.close();
        }
    }
}

const boxWebSocket = WebSocketSingleton.getInstance(bbgConf.env.wsBoxBaseUrl+"?t_code="+bbgConf.env.appUrlPrefix)
export {boxWebSocket};