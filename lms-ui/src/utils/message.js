import { ElMessage, ElMessageBox, ElNotification } from 'element-plus'

/**
 * ElementPlus 消息提示工具类
 */
class MessageUtil {
  /**
   * 成功消息提示
   * @param {string} message 消息内容
   * @param {number} duration 显示时间，毫秒，默认3000
   * @param {boolean} showClose 是否显示关闭按钮，默认false
   */
  static success(message, duration = 3000, showClose = false) {
    return ElMessage({
      message,
      type: 'success',
      duration,
      showClose
    })
  }

  /**
   * 警告消息提示
   * @param {string} message 消息内容
   * @param {number} duration 显示时间，毫秒，默认3000
   * @param {boolean} showClose 是否显示关闭按钮，默认false
   */
  static warning(message, duration = 3000, showClose = false) {
    return ElMessage({
      message,
      type: 'warning',
      duration,
      showClose
    })
  }

  /**
   * 信息消息提示
   * @param {string} message 消息内容
   * @param {number} duration 显示时间，毫秒，默认3000
   * @param {boolean} showClose 是否显示关闭按钮，默认false
   */
  static info(message, duration = 3000, showClose = false) {
    return ElMessage({
      message,
      type: 'info',
      duration,
      showClose
    })
  }

  /**
   * 错误消息提示
   * @param {string} message 消息内容
   * @param {number} duration 显示时间，毫秒，默认3000
   * @param {boolean} showClose 是否显示关闭按钮，默认false
   */
  static error(message, duration = 3000, showClose = false) {
    return ElMessage({
      message,
      type: 'error',
      duration,
      showClose
    })
  }

  /**
   * 确认对话框
   * @param {string} message 提示内容
   * @param {string} title 标题，默认"提示"
   * @param {string} confirmButtonText 确认按钮文本，默认"确定"
   * @param {string} cancelButtonText 取消按钮文本，默认"取消"
   * @param {string} type 类型，可选值：success/warning/info/error，默认"warning"
   * @returns {Promise} 返回Promise，点击确认时resolve，点击取消时reject
   */
  static confirm(
    message,
    title = '提示',
    confirmButtonText = '确定',
    cancelButtonText = '取消',
    type = 'warning'
  ) {
    return ElMessageBox.confirm(message, title, {
      confirmButtonText,
      cancelButtonText,
      type
    })
  }

  /**
   * 输入对话框
   * @param {string} message 提示内容
   * @param {string} title 标题，默认"提示"
   * @param {string} inputValue 输入框默认值，默认""
   * @param {string} confirmButtonText 确认按钮文本，默认"确定"
   * @param {string} cancelButtonText 取消按钮文本，默认"取消"
   * @returns {Promise} 返回Promise，确认时resolve输入的值，取消时reject
   */
  static prompt(
    message,
    title = '提示',
    inputValue = '',
    confirmButtonText = '确定',
    cancelButtonText = '取消'
  ) {
    return ElMessageBox.prompt(message, title, {
      inputValue,
      confirmButtonText,
      cancelButtonText
    })
  }

  /**
   * 成功通知
   * @param {string} title 通知标题
   * @param {string} message 通知内容
   * @param {number} duration 显示时间，毫秒，默认4500
   * @param {boolean} showClose 是否显示关闭按钮，默认true
   */
  static notifySuccess(title, message, duration = 4500, showClose = true) {
    return ElNotification({
      title,
      message,
      type: 'success',
      duration,
      showClose
    })
  }

  /**
   * 警告通知
   * @param {string} title 通知标题
   * @param {string} message 通知内容
   * @param {number} duration 显示时间，毫秒，默认4500
   * @param {boolean} showClose 是否显示关闭按钮，默认true
   */
  static notifyWarning(title, message, duration = 4500, showClose = true) {
    return ElNotification({
      title,
      message,
      type: 'warning',
      duration,
      showClose
    })
  }

  /**
   * 信息通知
   * @param {string} title 通知标题
   * @param {string} message 通知内容
   * @param {number} duration 显示时间，毫秒，默认4500
   * @param {boolean} showClose 是否显示关闭按钮，默认true
   */
  static notifyInfo(title, message, duration = 4500, showClose = true) {
    return ElNotification({
      title,
      message,
      type: 'info',
      duration,
      showClose
    })
  }

  /**
   * 错误通知
   * @param {string} title 通知标题
   * @param {string} message 通知内容
   * @param {number} duration 显示时间，毫秒，默认4500
   * @param {boolean} showClose 是否显示关闭按钮，默认true
   */
  static notifyError(title, message, duration = 4500, showClose = true) {
    return ElNotification({
      title,
      message,
      type: 'error',
      duration,
      showClose
    })
  }

  /**
   * 加载中提示
   * @param {string} message 提示内容，默认"加载中..."
   * @returns {object} 返回消息实例，可用于手动关闭
   */
  static loading(message = '加载中...') {
    return ElMessage({
      message,
      type: 'info',
      duration: 0,
      showClose: false,
      customClass: 'el-message-loading'
    })
  }

  /**
   * 关闭所有消息提示
   */
  static closeAll() {
    ElMessage.closeAll()
  }
}

export default MessageUtil
