# 伙伴匹配 - 后端服务

基于 Spring Boot 开发的社交交友平台后端系统，提供用户管理、队伍管理、AI 智能匹配等核心功能。

## 技术栈

- **后端框架**: Spring Boot 4.0.5
- **数据库**: MySQL
- **ORM 框架**: MyBatis-Plus 3.5.15
- **缓存**: Redis + Redisson
- **AI 集成**: Spring AI (通义千问 Qwen3.6-Plus)
- **API 文档**: Knife4j OpenAPI3
- **工具库**: Lombok, Gson, Apache Commons Lang3, Apache Commons Collections4
- **构建工具**: Maven
- **JDK 版本**: Java 17

## 核心功能

### 用户模块
- 用户注册、登录、注销
- 用户信息管理（更新、查询、删除）
- 基于标签的用户搜索
- 用户推荐（Redis 缓存预热）
- **AI 智能匹配** - 使用大语言模型分析用户标签，推荐最相似的用户

### 队伍模块
- 创建队伍（支持公开/私有/加密三种模式）
- 加入/退出队伍
- 队伍信息管理（更新、删除）
- 队伍列表查询（分页、条件筛选）
- 查看我创建/我加入的队伍

## 项目结构

```
friend/
├── sql/                          # 数据库建表脚本
│   └── create_table.sql
├── src/
│   ├── main/
│   │   ├── java/com/yupi/yupao/
│   │   │   ├── common/           # 通用响应类
│   │   │   │   ├── BaseResponse.java
│   │   │   │   ├── ErrorCode.java
│   │   │   │   └── ResultUtils.java
│   │   │   ├── config/           # 配置类
│   │   │   │   ├── MybatisPlusConfig.java
│   │   │   │   ├── RedisTemplateConfig.java
│   │   │   │   ├── RedissonConfig.java
│   │   │   │   └── SwaggerConfig.java
│   │   │   ├── constant/         # 常量定义
│   │   │   ├── controller/       # 控制器层
│   │   │   │   ├── UserController.java
│   │   │   │   └── TeamController.java
│   │   │   ├── exception/        # 异常处理
│   │   │   │   ├── BusinessException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── job/              # 定时任务
│   │   │   │   └── PreCacheJob.java
│   │   │   ├── mapper/           # MyBatis Mapper
│   │   │   ├── model/            # 数据模型
│   │   │   │   ├── domain/       # 实体类
│   │   │   │   ├── dto/          # 数据传输对象
│   │   │   │   ├── enums/        # 枚举类
│   │   │   │   ├── request/      # 请求参数
│   │   │   │   └── vo/           # 视图对象
│   │   │   ├── service/          # 业务逻辑层
│   │   │   │   ├── UserService.java
│   │   │   │   ├── TeamService.java
│   │   │   │   └── impl/         # 实现类
│   │   │   └── utils/            # 工具类
│   │   └── resources/
│   │       ├── application.yml   # 主配置文件
│   │       ├── application-dev.yml   # 开发环境配置
│   │       ├── application-prod.yml  # 生产环境配置
│   │       └── mapper/           # MyBatis XML 映射文件
│   └── test/                     # 单元测试
└── pom.xml
```

## 数据库设计

### 主要表结构

- **user** - 用户表（包含用户基本信息、标签、角色等）
- **team** - 队伍表（队伍信息、状态、密码等）
- **user_team** - 用户队伍关系表（多对多关联）
- **tag** - 标签表（支持层级标签结构）

详细建表语句请参考 [sql/create_table.sql](file:///e:/test/friend/sql/create_table.sql)

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 配置步骤

1. **克隆项目**
   ```bash
   git clone <your-repository-url>
   cd friend
   ```

2. **初始化数据库**
   ```bash
   mysql -u root -p < sql/create_table.sql
   ```

3. **修改配置文件**

   编辑 `src/main/resources/application.yml`，配置数据库和 Redis 连接信息：
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/yupao?serverTimezone=Asia/Shanghai
       username: your_username
       password: your_password
     redis:
       host: localhost
       port: 6379
   ```

4. **配置 AI 服务（可选）**

   如需使用 AI 用户匹配功能，需配置通义千问 API Key：
   ```yaml
   spring:
     ai:
       openai:
         api-key: your-api-key
         base-url: https://dashscope.aliyuncs.com/compatible-mode
   ```

5. **启动项目**
   ```bash
   mvn spring-boot:run
   ```

6. **访问 API 文档**

   启动后访问 Knife4j 文档：`http://localhost:8080/api/doc.html`

## API 接口

### 用户接口 (`/api/user`)

| 接口 | 方法 | 说明 |
|------|------|------|
| `/user/register` | POST | 用户注册 |
| `/user/login` | POST | 用户登录 |
| `/user/logout` | POST | 用户注销 |
| `/user/current` | GET | 获取当前登录用户 |
| `/user/search` | GET | 搜索用户（管理员） |
| `/user/search/tags` | GET | 根据标签搜索用户 |
| `/user/recommend` | GET | 推荐用户（带缓存） |
| `/user/update` | POST | 更新用户信息 |
| `/user/delete` | POST | 删除用户（管理员） |
| `/user/match` | GET | AI 智能匹配用户 |

### 队伍接口 (`/api/team`)

| 接口 | 方法 | 说明 |
|------|------|------|
| `/team/add` | POST | 创建队伍 |
| `/team/update` | POST | 更新队伍信息 |
| `/team/get` | GET | 获取队伍详情 |
| `/team/list` | GET | 查询队伍列表 |
| `/team/list/page` | GET | 分页查询队伍 |
| `/team/join` | POST | 加入队伍 |
| `/team/quit` | POST | 退出队伍 |
| `/team/delete` | POST | 删除/解散队伍 |
| `/team/list/my/create` | GET | 我创建的队伍 |
| `/team/list/my/join` | GET | 我加入的队伍 |

## 特色功能

### AI 智能匹配

系统集成了 Spring AI，通过大语言模型分析用户的标签信息，智能推荐兴趣相似的用户。相比传统算法匹配，AI 匹配能更好地理解标签之间的语义关联。

### Redis 缓存优化

- 用户推荐列表使用 Redis 缓存，减少数据库查询压力
- Session 存储使用 Redis，支持分布式部署
- 缓存预热定时任务，提前加载热门数据

### 分布式锁

使用 Redisson 实现分布式锁，保证并发场景下的数据一致性（如队伍加入、退出等操作）。

## 开发说明

### 环境切换

项目支持多环境配置：
- `application-dev.yml` - 开发环境
- `application-prod.yml` - 生产环境

通过 `application.yml` 中的 `spring.profiles.active` 切换环境。

### 统一响应格式

所有接口返回统一的 `BaseResponse<T>` 格式：
```json
{
  "code": 0,
  "data": {},
  "message": "success"
}
```

### 异常处理

系统使用全局异常处理器 `GlobalExceptionHandler`，统一处理业务异常和系统异常。

## 许可证

MIT License
