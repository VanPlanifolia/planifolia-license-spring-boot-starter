# 📦 Planifolia License Spring Boot Starter

**系统授权校验工具包**，用于实现服务端授权许可控制，包括：

- 基于公私钥的授权证书签发与验证；
- 拦截器自动校验授权有效期；
- 支持定时任务与接口方式动态刷新证书。

------

## 🧠 设计思路

该组件基于 `SHA256withRSA` 数字签名算法，并结合 Spring 的 `HandlerInterceptor` 实现系统运行时授权控制。

### 🔐 签名算法

- 使用 JDK 原生标准库 `java.security` 提供的 `Signature`、`KeyFactory`、`KeyPairGenerator` 等类；
- 对授权信息进行 RSA 非对称加密签名/验签；
- 签名结果使用 Base64 编码，便于存储和传输；
- 使用 FastJSON 保证字段顺序一致，避免签名验证失败。

### 🛡 拦截器机制

- 通过 Spring 的 `HandlerInterceptor` 实现对所有接口请求的授权校验；
- 默认放行接口 `/flushLicense`，允许运行时动态刷新证书；
- 若启用 `license.enableAutoRefresh=true`，将自动在每日凌晨 **02:00** 刷新授权文件。

------

## 🚀 快速开始

### 1. 克隆源码

> 仓库地址：https://github.com/VanPlanifolia/planifolia-license-spring-boot-starter

```bash
git clone https://github.com/VanPlanifolia/planifolia-license-spring-boot-starter.git
```

别忘了顺手点个 ⭐ Star 哦～

### 2. 本地安装

使用 IDE 或终端执行 `mvn install` 安装到本地 Maven 仓库：

> Maven 命令：

```bash
mvn clean install
```

------

### 3. 引入依赖

在你的 Spring Boot 项目的 `pom.xml` 中加入依赖：

```xml
<dependency>
    <groupId>van.planifolia.license</groupId>
    <artifactId>planifolia-license-spring-boot-starter</artifactId>
    <version>{planifolia-license-version}</version>
</dependency>
```

------

### 4. 配置参数（`application.yaml`）

```yaml
license:
  # 是否为开发调试模式（跳过授权校验）
  dev-model: false

  # 是否启用授权校验功能
  enable: true

  # 公钥文件路径
  public-key-path: "public.key"

  # 授权证书路径
  license-path: "license.lic"

  # 是否启用定时刷新（每日凌晨 2 点）
  enableAutoRefresh: true

  # 是否启用授权拦截器
  enableInterceptor: true
```

------

### 5. 生成证书授权文件

使用提供的工具类一键生成授权文件和公钥文件：

```java
public static void main(String[] args) throws Exception {
    LicenseGenTool.genLicense(
        "你的应用名称",
        new Date(), // 授权到期时间
        "客户名称",
        "license.lic", // 授权文件路径（支持相对或绝对路径）
        "public.key"   // 公钥文件路径（支持相对或绝对路径）
    );
}
```

------

### 6. 刷新授权证书

#### 🔧 手动刷新（适用于测试场景）

```java
@Resource
private LicenseManager licenseManager;

@Test
public void testFlushLicense() {
    licenseManager.load();
}
```

#### 🌐 接口刷新（适用于运行时）

```java
@GetMapping("/flushLicense")
public void flushLicense() {
    licenseManager.load();
}
```

此接口默认不经过拦截器校验，便于热更新证书。

------

## 📎 许可证示例结构

```json
{
  "customer": "客户名称",
  "expireTime": "2025-12-31 23:59:59",
  "product": "your-application",
  "signature": "Base64EncodedSignature..."
}
```

------

## ✨ 特性一览

| 功能                         | 说明                                                  |
| ---------------------------- | ----------------------------------------------------- |
| 🔐 基于 RSA 的非对称加密签名  | 使用 JDK 原生方式实现签名、验签，保障授权数据不被篡改 |
| 🧱 Spring Boot Starter 结构化 | 即插即用，配置简单，易于集成                          |
| 🚦 拦截器授权校验             | 拦截所有请求，确保服务仅在授权范围内运行              |
| 🔁 热更新授权证书             | 支持通过接口或定时任务方式动态刷新证书                |
| 🧪 支持开发模式与调试开关     | 开发阶段可快速调试，生产环境自动切换严格校验模式      |

------

## 📝 作者声明

作者：**Planifolia.Van**

欢迎 PR、欢迎 Issue、欢迎 Star ⭐！

如果该工具对你有所帮助，不妨留下一个 Star，鼓励作者持续更新 ☕。

邮箱 ：zhenyuncui@gamil.com

------

