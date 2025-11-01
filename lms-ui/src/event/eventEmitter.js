const eventNames = ['API:login', 'API:logout']
const eventEmitter = (function () {
    const events = {}
    function on(eventName, callback) {
        if (!events[eventName]) {
            events[eventName] = []
        }
        events[eventName].push(callback)
    }
    function emit(eventName, data) {
        if (events[eventName]) {
            events[eventName].forEach(callback => {
                callback(data)
            })
        }
    }
    return {
        on,
        emit
    }
})()

export default eventEmitter