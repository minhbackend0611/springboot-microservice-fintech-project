local key = KEYS[1]
local capacity = tonumber(ARGV[1])
local refill_rate = tonumber(ARGV[2])
local now = tonumber(ARGV[3])

local data = redis.call('HMGET', key, 'tokens', 'last_refill_time')
local tokens = tonumber(data[1])
local last_refill_time = tonumber(data[2])

if tokens == nil then
	tokens = capacity
	last_refill_time = now
else
	local elapsed = now - last_refill_time
	if elapsed > 0 then
		local tokens_to_add = elapsed * refill_rate
		tokens = math.min(capacity, tokens + tokens_to_add)
		last_refill_time = now
	end
end

if tokens >= 1 then
	tokens = tokens - 1
	redis.call('HMSET', key, 'tokens', tokens, 'last_refill_time', last_refill_time)
	redis.call('EXPIRE', key, 60)
	return 1
else
	redis.call('HMSET', key, 'tokens', tokens, 'last_refill_time', last_refill_time)
	return 0
end